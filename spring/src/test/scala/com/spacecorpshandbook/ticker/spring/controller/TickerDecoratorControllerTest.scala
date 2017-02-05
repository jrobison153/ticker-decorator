package com.spacecorpshandbook.ticker.spring.controller

import com.google.gson.Gson
import com.spacecorpshandbook.ticker.core.model.Ticker
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.RestAssuredMockMvc._
import org.hamcrest.CoreMatchers._
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class TickerDecoratorControllerTest extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  before {

    RestAssuredMockMvc.standaloneSetup(new TickerDecoratorController)
  }

  behavior of "ticker decorator controller when decorating a symbol without a chromosome"

  it should "send back a ticker with a chromosome" in {

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
