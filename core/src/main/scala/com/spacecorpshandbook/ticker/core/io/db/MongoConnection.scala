package com.spacecorpshandbook.ticker.core.io.db

import org.mongodb.scala.{MongoClient, MongoDatabase}

object MongoConnection {

  var TICKER_DB_NAME_ENV_VAR = "TICKER_DB_NAME"
  val TICKER_DB_PORT_ENV_VAR = "TICKER_DB_PORT"
  val TICKER_DB_HOST_ENV_VAR = "TICKER_DB_HOST"

  def isNonNullOrEmpty(value: String) = value != null && value.nonEmpty

  val DATABASE_NAME: String = {

    val envHold = System.getenv(TICKER_DB_NAME_ENV_VAR)

    if (isNonNullOrEmpty(envHold)) {

      envHold
    } else {

      "testStockData"
    }
  }

  val PORT: Int = {

    val envHold = System.getenv(TICKER_DB_PORT_ENV_VAR)

    if (isNonNullOrEmpty(envHold)) {

      envHold.toInt
    } else {

      12345
    }
  }

  val HOST: String = {

    val envHold = System.getenv(TICKER_DB_HOST_ENV_VAR)

    if (isNonNullOrEmpty(envHold)) {

      envHold
    } else {

      "localhost"
    }
  }

  System.out.println(s"Mongo Connection Url is mongodb://$HOST:$PORT")

  val mongoClient: MongoClient = MongoClient(s"mongodb://$HOST:$PORT")

  def getDefaultDatabase: MongoDatabase = {

    mongoClient.getDatabase(DATABASE_NAME)
  }
}
