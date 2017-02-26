package com.spacecorpshandbook.ticker.core.service

import com.spacecorpshandbook.ticker.core.map.BsonToBsonMappable
import com.spacecorpshandbook.ticker.core.model.Ticker
import org.bson.types.ObjectId
import org.mongodb.scala._
import org.scalatest.{AsyncFlatSpec, BeforeAndAfter, Matchers}

import scala.concurrent.Future
import scala.util.Success

/**
  * Test Requirements
  *  - Mongo database with test data available
  */
class DecoratorServiceITest extends AsyncFlatSpec
  with Matchers
  with BeforeAndAfter {

  var decoratorService: DecoratorService = _
  val mongoClient: MongoClient = MongoClient("mongodb://localhost")
  val mongoCollection = mongoClient.getDatabase("testStockData").getCollection("tickers")

  behavior of "a DecoratorService on an brand new, un-decorated, ticker"

  it should "add a chromosome" in {

    val findFuture = findSymbolAndDeleteFromDatabase()

    val testFuture = findFuture andThen {

      case Success(documents) => {

        val tickerDoc = documents.head

        val ticker: Ticker = new Ticker

        BsonToBsonMappable.map(tickerDoc, ticker)

        val decoratorService = new DecoratorService

        val updatedTicker: Ticker = decoratorService.addChromosome(ticker)

        findSymbolInDatabase(tickerDoc.getObjectId("_id"))
      }
    }

    testFuture map { documents =>

      val updatedTickerDoc = documents.head

      val updatedTicker = new Ticker

      BsonToBsonMappable.map(updatedTickerDoc, updatedTicker)

      assert(updatedTicker.chromosome.length > 0)
    }
  }

  def findSymbolAndDeleteFromDatabase(): Future[Seq[Document]] = {

    mongoCollection
      .findOneAndDelete(org.mongodb.scala.model.Filters.equal("ticker", "A"))
      .toFuture
  }

  def findSymbolInDatabase(id: ObjectId): Future[Seq[Document]] = {

    mongoCollection
      .find(org.mongodb.scala.model.Filters.equal("id", id))
      .toFuture
  }
}
