package com.spacecorpshandbook.ticker.core.io.db

import org.junit.Rule
import org.junit.contrib.java.lang.system.EnvironmentVariables
import org.scalatest.{FlatSpec, Matchers}

class MongoConnectionITest extends FlatSpec
  with Matchers {

  val _envVars: EnvironmentVariables = new EnvironmentVariables

  @Rule
  val envVars: EnvironmentVariables = _envVars

  val mongoSystemConfig = new MongoSystemConfig

  behavior of "A MongoConnection"

  it should "default the database name when the environment variable isn't set" in {

    val actualDatabaseName = mongoSystemConfig.getDatabaseName

    actualDatabaseName should equal("testStockData")
  }

  it should "default the database port when the environment variable isn't set" in {

    val actualDatabasePort = mongoSystemConfig.getDatabasePort

    actualDatabasePort should equal(12345)
  }

  it should "default the database host when the environment variable isn't set" in {

    val actualDatabaseHost = mongoSystemConfig.getDatabaseHost

    actualDatabaseHost should equal("localhost")
  }

  it should "default the username when the environment variable isn't set" in {

    val actualUserName = mongoSystemConfig.getUserName

    actualUserName should be(None)
  }

  it should "default the password when the environment variable isn't set" in {

    val actualUserPassword = mongoSystemConfig.getPassword

    actualUserPassword should be(None)
  }

  it should "set the database name to the value provided in the environment variable" in {

    val theDatabaseName: String = "reallygooddbname"

    envVars.set(MongoSystemConfig.TICKER_DB_NAME_ENV_VAR, theDatabaseName)

    val actualDatabaseName = mongoSystemConfig.getDatabaseName

    envVars.set(MongoSystemConfig.TICKER_DB_NAME_ENV_VAR, null)

    actualDatabaseName should equal(theDatabaseName)
  }

  it should "set the database port to the value provided in the environment variable" in {

    val theDatabasePort: Int = 33339

    envVars.set(MongoSystemConfig.TICKER_DB_PORT_ENV_VAR, theDatabasePort.toString)

    val actualDatabasePort = mongoSystemConfig.getDatabasePort

    envVars.set(MongoSystemConfig.TICKER_DB_PORT_ENV_VAR, null)

    actualDatabasePort should equal(theDatabasePort)
  }

  it should "set the database host to the value provided in the environment variable" in {

    val theDatabaseHost: String = "ahost"

    envVars.set(MongoSystemConfig.TICKER_DB_HOST_ENV_VAR, theDatabaseHost)

    val actualDatabaseHost = mongoSystemConfig.getDatabaseHost

    envVars.set(MongoSystemConfig.TICKER_DB_HOST_ENV_VAR, null)

    actualDatabaseHost should equal(theDatabaseHost)
  }

  it should "default the username when the environment variable is set" in {

    val theUserName: String = "dlister"

    envVars.set(MongoSystemConfig.TICKER_DB_USER_NAME_ENV_VAR, theUserName)

    val actualUserName = mongoSystemConfig.getUserName

    envVars.set(MongoSystemConfig.TICKER_DB_USER_NAME_ENV_VAR, null)

    actualUserName should be(Some(theUserName))
  }

  it should "default the password when the environment variable is set" in {

    val thePassword: String = "smeghead"

    envVars.set(MongoSystemConfig.TICKER_DB_PASSWORD_ENV_VAR, thePassword)

    val actualPassword = mongoSystemConfig.getPassword

    envVars.set(MongoSystemConfig.TICKER_DB_PASSWORD_ENV_VAR, null)

    actualPassword should be(Some(thePassword))
  }

  it should "set the mongo connection string correctly when there are no credentials" in {

    val noCredentialsMongoUrlRegEx = """mongodb\:\/\/[a-zA-Z]+\:[0-9]+\/[\w]+"""

    MongoConnection.composeConnectionUrl should fullyMatch regex noCredentialsMongoUrlRegEx
  }

  it should "set the mongo connection string correctly when there are credentials" in {

    val userName: String = "ausername"
    val passowrd: String = "apassword"

    envVars.set(MongoSystemConfig.TICKER_DB_USER_NAME_ENV_VAR, userName)
    envVars.set(MongoSystemConfig.TICKER_DB_PASSWORD_ENV_VAR, passowrd)

    val withCredentialsMongoUrlRegEx = s"mongodb\\:\\/\\/$userName\\:$passowrd@[a-zA-Z]+\\:[0-9]+\\/[\\w]+".r

    MongoConnection.composeConnectionUrl should fullyMatch regex withCredentialsMongoUrlRegEx
  }
}
