package com.spacecorpshandbook.ticker.core.io.db

import com.spacecorpshandbook.ticker.core.constant.Database.STOCK_TICKER_COLLECTION
import de.flapdoodle.embed.mongo.config.{IMongodConfig, MongodConfigBuilder, Net}
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.{MongodExecutable, MongodProcess, MongodStarter}
import de.flapdoodle.embed.process.runtime.Network
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.{MongoCollection, MongoDatabase}

import scala.io.Source

object TickerDatabaseSetup {

  var mongodExecutable: MongodExecutable = _
  var mongoD: MongodProcess = _
  var persistence: Persistence = _
  val starter: MongodStarter = MongodStarter.getDefaultInstance

  try {

    val mongoSystemConfig: MongoSystemConfig = new MongoSystemConfig

    val mongodConfig: IMongodConfig = new MongodConfigBuilder()
      .version(Version.Main.PRODUCTION)
      .net(new Net(mongoSystemConfig.getDatabaseHost, mongoSystemConfig.getDatabasePort, Network.localhostIsIPv6()))
      .build

    mongodExecutable = starter.prepare(mongodConfig)
    mongoD = mongodExecutable.start()
  }
  catch {

    case ex: Exception =>

      System.err.println(s"""Error setting up database for test: ${ex.toString}""")

      if (mongoD != null) {

        mongoD.stop()
      }

      if (mongodExecutable != null) {

        mongodExecutable.stop()
      }
  }


  def databaseSetup = {

    val mongoDatabase: MongoDatabase = MongoConnection.getDefaultDatabase

    var collection: MongoCollection[Document] = mongoDatabase.getCollection(STOCK_TICKER_COLLECTION)

    val tickerDataStream = getClass.getClassLoader.getResourceAsStream("ticker-data.json")
    val linesOfJson = Source.fromInputStream(tickerDataStream).getLines
    var docsToInsert: Seq[Document] = Seq()


    for (jsonLine <- linesOfJson) {

      docsToInsert = docsToInsert :+ Document(jsonLine)
    }

    val initDoneFuture = collection.insertMany(docsToInsert).toFuture()

    initDoneFuture
  }

  def databaseCleanup = {

    val mongoDatabase: MongoDatabase = MongoConnection.getDefaultDatabase
    var collection: MongoCollection[Document] = mongoDatabase.getCollection(STOCK_TICKER_COLLECTION)

    collection.drop().toFuture()
  }
}