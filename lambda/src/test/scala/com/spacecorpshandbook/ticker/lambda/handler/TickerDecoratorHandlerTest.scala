package com.spacecorpshandbook.ticker.lambda.handler

import java.io.{InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.Context
import com.google.gson.{Gson, JsonObject, JsonParser}
import com.spacecorpshandbook.ticker.core.model.Ticker
import org.apache.commons.io.IOUtils
import org.hamcrest.CoreMatchers.containsString
import org.junit.runner.RunWith
import org.mockito.Matchers.{any, argThat}
import org.mockito.Mockito.{mock, when}
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}


@PrepareForTest(Array {
  classOf[IOUtils]
})
@RunWith(classOf[PowerMockRunner])
class TickerDecoratorHandlerTest extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  var mockContext: Context = _
  var mockOutputStream: OutputStream = _
  var mockInputStream: InputStream = _
  var inputJsonObject: JsonObject = _
  var inputObj: JsonObject = _
  var gson: Gson = _

  before {

    gson = new Gson
    mockContext = mock(classOf[Context])
    mockOutputStream = mock(classOf[OutputStream])
    mockInputStream = mock(classOf[InputStream])

    val classLoader: ClassLoader = classOf[TickerDecoratorHandlerTest].getClassLoader

    val requestStream = classLoader.getResourceAsStream("ApiProxyRequestTemplate.json")

    val parser: JsonParser = new JsonParser

    inputObj = parser.parse(IOUtils.toString(requestStream, "UTF-8")).getAsJsonObject

    /*
    Need to power mock the static after it's use above
     */
    PowerMockito.mockStatic(classOf[IOUtils])
  }

  behavior of "ticker decorator handler when decorating a symbol without a chromosome"

  it should "send back a ticker with a chromosome" in {

    val symbolName: String = "abcd"
    val expectedResponseMessage: String = "Decorated symbol " + symbolName

    val ticker: Ticker = new Ticker
    ticker.ticker = symbolName

    inputObj.remove("body")
    inputObj.addProperty("body", gson.toJson(ticker))

    /*
   Need to mock the IOUtils.toString method to return the input string as this will also be mocked. Quick reasearch didn't turn
   up a way to just mock static method IOUtils.write
    */
    when(IOUtils.toString(any(classOf[InputStream]), any(classOf[String]))).thenReturn(inputObj.toString)

    val handler: TickerDecoratorHandler = new TickerDecoratorHandler
    handler.decorateTicker(mockInputStream, mockOutputStream, mockContext)

    PowerMockito.verifyStatic()
    IOUtils.write(argThat(containsString(expectedResponseMessage)), any(classOf[OutputStream]), org.mockito.Matchers.eq("UTF-8"))
  }

  it should "deserialize a fully populated ticker without error" in {

    val ticker: Ticker = new Ticker

    ticker.ticker = "foo"
    ticker.date = "1999-11-29"
    ticker.open = BigDecimal(87.22)
    ticker.close = BigDecimal(89.21)
    ticker.high = BigDecimal(90.11)
    ticker.low = BigDecimal(80.11)

    inputObj.remove("body")
    inputObj.addProperty("body", gson.toJson(ticker))

    when(IOUtils.toString(any(classOf[InputStream]), any(classOf[String]))).thenReturn(inputObj.toString)

    val handler: TickerDecoratorHandler = new TickerDecoratorHandler
    handler.decorateTicker(mockInputStream, mockOutputStream, mockContext)
  }

}