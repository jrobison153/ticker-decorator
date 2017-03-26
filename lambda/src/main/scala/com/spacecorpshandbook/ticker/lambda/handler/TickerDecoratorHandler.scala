package com.spacecorpshandbook.ticker.lambda.handler

import java.io.{InputStream, OutputStream}
import java.util.HashMap

import com.amazonaws.services.lambda.runtime.Context
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.spacecorpshandbook.ticker.core.model.{Ticker, TickerDecoratorResponse}
import com.spacecorpshandbook.ticker.lambda.proxy.ApiGatewayProxyResponse

/**
  * Amazon Lambda handler adapter for the ticker decorator application
  */
class TickerDecoratorHandler {

  val objMapper: ObjectMapper = new ObjectMapper().findAndRegisterModules

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

    objMapper.readValue(body.toString, classOf[Ticker])
  }

  private[this] def decorateTicker(ticker: Ticker): TickerDecoratorResponse = {

    val decroatorResponse = new TickerDecoratorResponse

    decroatorResponse.message = "Decorated symbol " + ticker.ticker

    decroatorResponse
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
}