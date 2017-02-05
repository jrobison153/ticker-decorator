package com.spacecorpshandbook.ticker.lambda.handler

import java.io.{ByteArrayOutputStream, InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.Context
import com.google.gson.{Gson, JsonObject, JsonParser}
import com.spacecorpshandbook.ticker.core.model.{Ticker, TickerDecoratorResponse}
import com.spacecorpshandbook.ticker.lambda.proxy.ApiGatewayProxyResponse
import org.apache.commons.io.IOUtils
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}


class TickerDecoratorHandlerTest extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  var mockContext: Context = _
  var outputStream: OutputStream = _
  var inputStream: InputStream = _
  var inputJsonObject: JsonObject = _
  var inputObj: JsonObject = _
  var gson: Gson = _

  before {

    gson = new Gson
    mockContext = mock(classOf[Context])
    outputStream = new ByteArrayOutputStream

    val classLoader: ClassLoader = classOf[TickerDecoratorHandlerTest].getClassLoader

    val requestAsStream = classLoader.getResourceAsStream("ApiProxyRequestTemplate.json")

    val parser: JsonParser = new JsonParser

    inputObj = parser.parse(IOUtils.toString(requestAsStream, "UTF-8")).getAsJsonObject
  }

  behavior of "ticker decorator handler when decorating a symbol without a chromosome"


  it should "send back a ticker with a chromosome" in {

    val symbolName: String = "abcd"
    val expectedResponseMessage: String = "Decorated symbol " + symbolName

    val ticker: Ticker = new Ticker
    ticker.ticker = symbolName

    setupInputStreamForTicker(ticker)

    val handler: TickerDecoratorHandler = new TickerDecoratorHandler
    handler.decorateTicker(inputStream, outputStream, mockContext)

    val decoratorResponse = parseOutputStream()

    decoratorResponse.message should equal(expectedResponseMessage)
  }

    it should "deserialize a fully populated ticker without error" in {

      val ticker: Ticker = new Ticker

      ticker.ticker = "foo"
      ticker.date = "1999-11-29"
      ticker.open = BigDecimal(87.22)
      ticker.close = BigDecimal(89.21)
      ticker.high = BigDecimal(90.11)
      ticker.low = BigDecimal(80.11)

      setupInputStreamForTicker(ticker)

      val handler: TickerDecoratorHandler = new TickerDecoratorHandler
      handler.decorateTicker(inputStream, outputStream, mockContext)
    }


  /* ================ Utility Functions ============================ */

  private def setupInputStreamForTicker(ticker: Ticker): Unit = {

    inputObj.remove("body")
    inputObj.addProperty("body", gson.toJson(ticker))

    inputStream = IOUtils.toInputStream(inputObj.toString, "UTF-8")
  }

  def parseOutputStream(): TickerDecoratorResponse = {

    val responseAsString = outputStream.toString
    val gatewayResponse = gson.fromJson(responseAsString, classOf[ApiGatewayProxyResponse])
    val decoratorResponseAsString = gatewayResponse.getBody

    gson.fromJson(decoratorResponseAsString, classOf[TickerDecoratorResponse])
  }
}