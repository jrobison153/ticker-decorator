package com.spacecorpshandbook.ticker.lambda.handler

import java.io.{InputStream, OutputStream}
import java.util.HashMap
import java.util.concurrent.TimeUnit

import com.amazonaws.services.lambda.runtime.Context
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.spacecorpshandbook.ticker.core.calculator.SimpleMovingAverageCalculator
import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeEncoder
import com.spacecorpshandbook.ticker.core.io.db.{MongoConnection, TickerPersistence}
import com.spacecorpshandbook.ticker.core.model.{Ticker, TickerDecoratorResponse}
import com.spacecorpshandbook.ticker.core.service.DecoratorService
import com.spacecorpshandbook.ticker.lambda.proxy.ApiGatewayProxyResponse

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Amazon Lambda handler adapter for the ticker decorator application
  */
class TickerDecoratorHandler {

  var decoratorService: DecoratorService = _
  val objMapper: ObjectMapper = new ObjectMapper()
    .registerModule(new JavaTimeModule)
    .registerModule(DefaultScalaModule)

  def decorateTicker(request: InputStream, response: OutputStream, context: Context): Unit = {

    val ticker: Ticker = convertStreamToTicker(request, context)

    val decoratorResponse: TickerDecoratorResponse = decorateTicker(ticker)

    val output = createApiGatewayResponse(response, decoratorResponse)

    objMapper.findAndRegisterModules

    objMapper writeValue(response, output)
  }

  private[this] def convertStreamToTicker(request: InputStream, context: Context): Ticker = {

    val httpRequest: JsonNode = objMapper readTree request

    val body: JsonNode = httpRequest get "body"

    objMapper.readValue(body.textValue, classOf[Ticker])
  }

  private[this] def decorateTicker(ticker: Ticker): TickerDecoratorResponse = {

    initializeDecoratorService

    val decorationDone = decoratorService.addChromosome(ticker)

    /*
   TODO: is there a better way than blocking the future? This would be easy in Node.
    */
    val updatedTicker = Await.result(decorationDone, Duration(30, TimeUnit.SECONDS))

    val decoratorResponse = new TickerDecoratorResponse
    decoratorResponse.ticker = updatedTicker
    decoratorResponse.message = "Decorated symbol " + ticker.ticker

    decoratorResponse
  }

  private[this] def createApiGatewayResponse(outputStream: OutputStream, decoratorResponse: TickerDecoratorResponse): ApiGatewayProxyResponse = {

    val apiGatewayProxyResponse = new ApiGatewayProxyResponse

    apiGatewayProxyResponse.body = objMapper.writeValueAsString(decoratorResponse)

    apiGatewayProxyResponse.statusCode = "200"

    val headerValues = new HashMap[String, String]
    headerValues put("Content-Type", "application/json")

    apiGatewayProxyResponse.headers = headerValues

    apiGatewayProxyResponse
  }

  def initializeDecoratorService = {

    /*
    In theory the first lambda function to execute will initialize the connection, then
    all the functions should be able to share the same connection
     */
    if (decoratorService == null) {

      System.out.println("Initializing connection to database...")

      val database = MongoConnection.getDefaultDatabase
      val persistence = new TickerPersistence(database)

      val movingAverageCalculator = new SimpleMovingAverageCalculator
      val encoder = new ChromosomeEncoder(movingAverageCalculator)

      decoratorService = new DecoratorService(persistence, encoder)
    }
  }
}