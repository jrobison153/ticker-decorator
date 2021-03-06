package com.spacecorpshandbook.ticker.spring.controller

import java.time.LocalDate

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeDecoder
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.spring.spy.StringMessagePublisherSpy
import com.spacecorpshandbook.ticker.spring.stub.CreatesDefaultChromosomeDecoratorServiceStub
import com.spacecorpshandbook.ticker.spring.wrapper.TickerServiceMirror
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.RestAssuredMockMvc._
import org.hamcrest.CoreMatchers._
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class TickerDecoratorControllerTest extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  val objMapper : ObjectMapper = new ObjectMapper()
    .findAndRegisterModules
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  var messagePublisherSpy : StringMessagePublisherSpy = _
  var tickerAsString: String = _

  before {

    val decoratorService: TickerServiceMirror = new CreatesDefaultChromosomeDecoratorServiceStub
    messagePublisherSpy = new StringMessagePublisherSpy

    RestAssuredMockMvc.standaloneSetup(new TickerDecoratorController(decoratorService, messagePublisherSpy))

    val symbolName = "abc"

    val ticker: Ticker = new Ticker
    ticker.ticker = symbolName
    ticker.open = BigDecimal(45.21)
    ticker.close = BigDecimal(45.21)
    ticker.high = BigDecimal(45.21)
    ticker.low = BigDecimal(45.21)
    ticker.date = LocalDate.now

    tickerAsString = objMapper.writeValueAsString(ticker)
  }

  behavior of "ticker decorator controller when decorating a symbol without a chromosome"

  val expectedChromosome: String = ChromosomeDecoder.DEFAULT_CHROMOSOME

  it should "send back a ticker with a chromosome" in {

    given.
      body(tickerAsString)
      .contentType(ContentType.JSON)
      .when
      .post("/ticker/decorate")
      .then
      .statusCode(200)
      .body("ticker.chromosome", equalTo(expectedChromosome))
  }
}
