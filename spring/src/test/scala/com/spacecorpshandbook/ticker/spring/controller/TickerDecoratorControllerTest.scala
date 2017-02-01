package com.spacecorpshandbook.ticker.spring.controller

import com.google.gson.Gson
import com.spacecorpshandbook.ticker.core.model.Ticker
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.RestAssuredMockMvc._
import org.hamcrest.CoreMatchers._
import org.junit.{Before, Test}

class TickerDecoratorControllerTest {

  @Before
  def setup(): Unit = {

    RestAssuredMockMvc.standaloneSetup(new TickerDecoratorController)
  }

  @Test
  def givenTickerWhenDecoratedThenTheSymbolNameIsEchoedBack(): Unit = {

    val symbolName = "abc"

    val ticker: Ticker = new Ticker
    ticker.ticker = symbolName

    val gson: Gson = new Gson

    given.
      body(gson.toJson(ticker))
      .contentType(ContentType.JSON)
      .when
      .post("/ticker/decorate")
      .then
      .statusCode(200)
      .body("message", containsString(symbolName))

  }
}
