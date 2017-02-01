package com.spacecorpshandbook.ticker.lambda.handler

import java.io.{IOException, InputStream, OutputStream}
import java.util

import com.amazonaws.services.lambda.runtime.Context
import com.google.gson.{Gson, JsonObject, JsonParser}
import com.spacecorpshandbook.ticker.core.model.{Ticker, TickerDecoratorResponse}
import com.spacecorpshandbook.ticker.lambda.proxy.ApiGatewayProxyResponse
import org.apache.commons.io.IOUtils


/**
  * Amazon Lambda handler adapter for the ticker decorator application
  */
class TickerDecoratorHandler {

  def decorateTicker(request: InputStream, response: OutputStream, context: Context): Unit = {

    val ticker: Ticker = convertStreamToTicker(request, context)

    val decoratorResponse: TickerDecoratorResponse = decorateTicker(ticker)

    val output = convertTickerToString(response, decoratorResponse)

    IOUtils.write(output, response, "UTF-8")
  }

  private[this] def convertStreamToTicker(request: InputStream, context: Context): Ticker = {


    val parser: JsonParser = new JsonParser
    val gson: Gson = new Gson
    var inputObj: JsonObject = null
    val logger = context.getLogger

    try {

      inputObj = parser.parse(IOUtils.toString(request, "UTF-8")).getAsJsonObject

      val body: String = inputObj.get("body").getAsString
      val ticker: Ticker = gson.fromJson(body, classOf[Ticker])

      ticker

    } catch {

      case e: IOException =>

        logger.log("Error while reading request\n" + e.getMessage)
        throw new RuntimeException(e.getMessage)

    }
  }

  private[this] def decorateTicker(ticker: Ticker): TickerDecoratorResponse = {

    val decroatorResponse = new TickerDecoratorResponse

    decroatorResponse.setMessage("Decorated symbol " + ticker.ticker)

    decroatorResponse
  }

  private[this] def convertTickerToString(outputStream: OutputStream, decoratorResponse: TickerDecoratorResponse): String = {

    val apiGatewayProxyResponse = new ApiGatewayProxyResponse

    val gson: Gson = new Gson

    apiGatewayProxyResponse.setBody(gson.toJson(decoratorResponse))

    apiGatewayProxyResponse.setStatusCode("200")

    val headerValues = new util.HashMap[String, String]

    headerValues put("Content-Type", "application/json")

    apiGatewayProxyResponse.setHeaders(headerValues)

    val output: String = gson.toJson(apiGatewayProxyResponse)

    output
  }
}