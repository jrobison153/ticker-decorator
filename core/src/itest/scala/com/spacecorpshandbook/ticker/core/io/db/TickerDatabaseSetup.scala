package com.spacecorpshandbook.ticker.core.io.db

import com.spacecorpshandbook.ticker.core.constant.Database._
import de.flapdoodle.embed.mongo.config.{IMongodConfig, MongodConfigBuilder, Net}
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.{MongodExecutable, MongodProcess, MongodStarter}
import de.flapdoodle.embed.process.runtime.Network
import org.mongodb.scala.{MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.Future
import scala.io.Source

trait TickerDatabaseSetup {

  var mongodExecutable: MongodExecutable = _
  var mongoDatabase: MongoDatabase = _
  var mongoD: MongodProcess = _
  var persistence: Persistence = _
  var initDoneFuture: Future[_] = _
  var collection: MongoCollection[Document] = _
  val starter: MongodStarter = MongodStarter.getDefaultInstance

  def databaseSetup = {

    try {
      val mongodConfig: IMongodConfig = new MongodConfigBuilder()
        .version(Version.Main.PRODUCTION)
        .net(new Net(MongoConnection.host, MongoConnection.port, Network.localhostIsIPv6()))
        .build

      mongodExecutable = starter.prepare(mongodConfig)
      mongoD = mongodExecutable.start()
      mongoDatabase = MongoConnection.getDefaultDatabase

      collection = mongoDatabase.getCollection(STOCK_TICKER_COLLECTION)

      val tickerDataStream = getClass.getClassLoader.getResourceAsStream("tickerdata.json")
      val linesOfJson = Source.fromInputStream(tickerDataStream).getLines
      var docsToInsert: Seq[Document] = Seq()

      for (jsonLine <- linesOfJson) {
        docsToInsert = docsToInsert :+ Document(jsonLine)
      }

      initDoneFuture = collection.insertMany(docsToInsert).toFuture()
    }
    catch {

      case ex: Exception =>
        if (mongodExecutable != null) {

          mongoD.stop()
          mongodExecutable.stop()
        }
    }
  }

  def databaseCleanup = {

    if (mongodExecutable != null) {

      mongoD.stop()
      mongodExecutable.stop()
    }
  }
}
