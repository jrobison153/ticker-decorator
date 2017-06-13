package com.spacecorpshandbook.ticker.core.io.db

import org.mongodb.scala.{MongoClient, MongoDatabase}

object MongoConnection {

  var databaseName: String = _

  var port: Int = _

  var host: String = _

  private val connectionUrl = composeConnectionUrl

  System.out.println(connectionUrl)

  val mongoClient: MongoClient = MongoClient(connectionUrl)

  def composeConnectionUrl = {

    val mongoSystemConfig: MongoSystemConfig = new MongoSystemConfig

    databaseName = mongoSystemConfig.getDatabaseName
    port = mongoSystemConfig.getDatabasePort
    host = mongoSystemConfig.getDatabaseHost
    val userName = mongoSystemConfig.getUserName
    val password = mongoSystemConfig.getPassword

    (userName, password) match {

      case (Some(aUserName), Some(aPassword)) => s"mongodb://$aUserName:$aPassword@$host:$port/$databaseName"
      case _ => s"mongodb://$host:$port/$databaseName"
    }
  }

  def getDefaultDatabase: MongoDatabase = {

    mongoClient.getDatabase(databaseName)
  }
}

class MongoSystemConfig {

  def getDatabaseHost: String = {

    getStringEnvValue(MongoSystemConfig.TICKER_DB_HOST_ENV_VAR) match {
      case Some(value) => value
      case None => "localhost"
    }
  }

  def getDatabaseName: String = {

    getStringEnvValue(MongoSystemConfig.TICKER_DB_NAME_ENV_VAR) match {
      case Some(value) => value
      case None => "testStockData"
    }
  }

  def getDatabasePort: Int = {

    val envHold = System.getenv(MongoSystemConfig.TICKER_DB_PORT_ENV_VAR)

    if (isNonNullOrEmpty(envHold)) {

      envHold.toInt
    } else {

      12345
    }
  }

  def getPassword: Option[String] = {

    getStringEnvValue(MongoSystemConfig.TICKER_DB_PASSWORD_ENV_VAR)
  }

  def getUserName: Option[String] = {

    getStringEnvValue(MongoSystemConfig.TICKER_DB_USER_NAME_ENV_VAR)
  }

  private def getStringEnvValue(envVarName: String): Option[String] = {

    val envHold = System.getenv(envVarName)

    if (isNonNullOrEmpty(envHold)) {

      Some(envHold)
    } else {

      None
    }
  }

  private def isNonNullOrEmpty(value: String) = value != null && value.nonEmpty
}

object MongoSystemConfig {

  val TICKER_DB_HOST_ENV_VAR = "TICKER_DB_HOST"
  val TICKER_DB_NAME_ENV_VAR = "TICKER_DB_NAME"
  val TICKER_DB_PASSWORD_ENV_VAR = "TICKER_DB_PASSWORD"
  val TICKER_DB_PORT_ENV_VAR = "TICKER_DB_PORT"
  val TICKER_DB_USER_NAME_ENV_VAR = "TICKER_DB_USER"
}