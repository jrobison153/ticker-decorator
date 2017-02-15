package com.spacecorpshandbook.ticker.core.io.db

import org.mongodb.scala.{MongoClient, MongoDatabase}

/**
  */
object MongoConnection {

  // TODO: this needs to be configurable
  private val DEFAULT_DATABASE_NAME = "testStockData"

  // TODO: this needs to be configurable
  val mongoClient: MongoClient = MongoClient("mongodb://localhost")

  def getDefaultDatabase: MongoDatabase = {

    mongoClient.getDatabase(DEFAULT_DATABASE_NAME)
  }

}
