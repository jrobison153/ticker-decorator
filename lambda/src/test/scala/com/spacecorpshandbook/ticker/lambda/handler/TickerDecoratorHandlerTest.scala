package com.spacecorpshandbook.ticker.lambda.handler

import java.io.{ByteArrayOutputStream, InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.Context
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeDecoder
import com.spacecorpshandbook.ticker.core.model.{Ticker, TickerDecoratorResponse}
import com.spacecorpshandbook.ticker.lambda.proxy.ApiGatewayProxyResponse
import com.spacecorpshandbook.ticker.lambda.stub.CreatesDefaultChromosomeDecoratorServiceStub
import org.apache.commons.io.IOUtils
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}


class TickerDecoratorHandlerTest extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  var mockContext: Context = _
  var outputStream: OutputStream = _
  var inputStream: InputStream = _
  var inputObj: JsonNode = _
  val objMapper: ObjectMapper = new ObjectMapper().findAndRegisterModules
  var handler: TickerDecoratorHandler = _

  val decoratorServiceStub = new CreatesDefaultChromosomeDecoratorServiceStub

  before {

    mockContext = mock(classOf[Context])
    outputStream = new ByteArrayOutputStream

    val classLoader: ClassLoader = classOf[TickerDecoratorHandlerTest].getClassLoader

    val requestAsStream = classLoader.getResourceAsStream("ApiProxyRequestTemplate.json")

    inputObj = objMapper.readTree(requestAsStream)

    handler = new TickerDecoratorHandler
    handler.decoratorService = decoratorServiceStub
  }

  behavior of "ticker decorator handler when decorating a symbol without a chromosome"

  it should "deserialize raw input stream" in {

    val expectedChromosome = ChromosomeDecoder.DEFAULT_CHROMOSOME

    val classLoader: ClassLoader = classOf[TickerDecoratorHandlerTest].getClassLoader

    inputStream = classLoader.getResourceAsStream("ApiProxyRequestTemplate.json")

    handler.decorateTicker(inputStream, outputStream, mockContext)

    val decoratorResponse = parseOutputStream()

    decoratorResponse.ticker.chromosome should equal(expectedChromosome)
  }

  it should "send back a ticker with a chromosome" in {

    val expectedChromosome = ChromosomeDecoder.DEFAULT_CHROMOSOME
    val symbolName: String = "abcd"

    val ticker: Ticker = new Ticker
    ticker.ticker = symbolName

    setupInputStreamForTicker(ticker)

    handler.decorateTicker(inputStream, outputStream, mockContext)

    val decoratorResponse = parseOutputStream()

    decoratorResponse.ticker.chromosome should equal(expectedChromosome)
  }

  /* ================ Utility Functions ============================ */

  private def setupInputStreamForTicker(ticker: Ticker): Unit = {

    val tickerAsJsonNode: JsonNode = objMapper.valueToTree(ticker)
    inputObj.asInstanceOf[ObjectNode].put("body", tickerAsJsonNode.toString)

    inputStream = IOUtils.toInputStream(inputObj.toString, "UTF-8")
  }

  def parseOutputStream(): TickerDecoratorResponse = {

    val responseAsString = outputStream.toString
    val gatewayResponse = objMapper.readValue(responseAsString, classOf[ApiGatewayProxyResponse])
    val decoratorResponseAsString = gatewayResponse.getBody

    objMapper.readValue(decoratorResponseAsString, classOf[TickerDecoratorResponse])
  }
}