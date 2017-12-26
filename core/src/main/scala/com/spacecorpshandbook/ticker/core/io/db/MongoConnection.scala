package com.spacecorpshandbook.ticker.core.io.db

import org.mongodb.scala.{MongoClient, MongoDatabase}

object MongoConnection {

  private val mongoSystemConfig: MongoSystemConfig = new MongoSystemConfig

  private val connectionUrl = mongoSystemConfig.composeConnectionUrl

  System.out.println(s"Mongo Connection String: $connectionUrl")

  val mongoClient: MongoClient = MongoClient(connectionUrl)

  def getDefaultDatabase: MongoDatabase = {

    mongoClient.getDatabase(mongoSystemConfig.getDatabaseName)
  }
}
