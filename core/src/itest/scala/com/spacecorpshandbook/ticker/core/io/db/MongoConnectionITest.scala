package com.spacecorpshandbook.ticker.core.io.db

import org.scalatest.{FlatSpec, Matchers}

class MongoConnectionITest extends FlatSpec
  with Matchers {

  behavior of "A MongoConnection"

  it should "default the database name when the environment variable isn't set" in {

    MongoConnection.DATABASE_NAME should equal("testStockData")
  }

  it should "default the database port when the environment variable isn't set" in {

    MongoConnection.PORT should equal(12345)
  }

  it should "default the database host when the environment variable isn't set" in {

    MongoConnection.HOST should equal("localhost")
  }
}
