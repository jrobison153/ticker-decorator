package com.spacecorpshandbook.ticker.io.db

import org.mongodb.scala.{MongoClient, MongoDatabase}

/**
  */
object MongoConnection {

  private val DEFAULT_DATABASE_NAME = "stockData"

  val mongoClient: MongoClient = MongoClient("mongodb://localhost")

  def getDefaultDatabase: MongoDatabase = {

    mongoClient.getDatabase(DEFAULT_DATABASE_NAME)
  }

}
