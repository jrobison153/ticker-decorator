package com.spacecorpshandbook.ticker.core.service

import com.spacecorpshandbook.ticker.core.calculator.SimpleMovingAverageCalculator
import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeEncoder
import com.spacecorpshandbook.ticker.core.constant.Database._
import com.spacecorpshandbook.ticker.core.map.BsonToBsonMappable
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.search.DatabaseSearcher
import org.bson.BsonString
import org.bson.types.ObjectId
import org.mongodb.scala._
import org.mongodb.scala.bson.BsonValue
import org.mongodb.scala.model.Sorts.descending
import org.mongodb.scala.model.Updates.unset
import org.scalatest.{AsyncFlatSpec, BeforeAndAfter, Matchers}

import scala.concurrent.Future


/**
  * Test Requirements
  *  - Mongo database with test data available
  */
class DecoratorServiceITest extends AsyncFlatSpec
  with Matchers
  with BeforeAndAfter {

  var decoratorService: DecoratorService = _
  val mongoClient: MongoClient = MongoClient("mongodb://localhost")
  val mongoCollection = mongoClient.getDatabase("testStockData").getCollection(STOCK_TICKER_COLLECTION)

  behavior of "a DecoratorService on an brand new, un-decorated, ticker"

  it should "add a chromosome" in {

    for {

      tickerDoc <- findSymbolWithYearOfHistoryInDatabase()
      tickerDocNoChromosomeSeq <- clearChromosomeFieldInDatabase(tickerDoc)
      updatedTicker <- {

        val ticker: Ticker = new Ticker
        val tickerDocNoChromosome = tickerDocNoChromosomeSeq.head

        BsonToBsonMappable.map(tickerDocNoChromosome, ticker)

        val movingAverageCalculator = new SimpleMovingAverageCalculator
        val chromosomeEncoder = new ChromosomeEncoder(ticker, movingAverageCalculator)
        val dbSearcher = new DatabaseSearcher
        val decoratorService = new DecoratorService(dbSearcher, chromosomeEncoder)

        decoratorService addChromosome ticker

      }

      updatedDocuments <- findSymbolInDatabase(updatedTicker.id)

    } yield {

      val updatedTickerDoc = updatedDocuments.head

      val chromosome = updatedTickerDoc.get("chromosome") match {

        case Some(aChromosome) => aChromosome
        case None => new BsonString("")
      }

      assert(chromosome.asString.getValue.length > 0)
    }
  }

  def findSymbolWithYearOfHistoryInDatabase() = {

    val findFuture = mongoCollection
      .find(org.mongodb.scala.model.Filters.equal("ticker", "A"))
      .sort(descending("date"))
      .limit(300)
      .toFuture

    findFuture map (allDocuments => {

      assert(allDocuments.length == 300)

      allDocuments.head

    })
  }

  def clearChromosomeFieldInDatabase(tickerDoc: Document) = {

    val objectId = tickerDoc.get("_id") match {

      case Some(bsonThing: BsonValue) => bsonThing
      case None => throw new RuntimeException("Could not get ObjectId from Document")
    }

    mongoCollection
      .findOneAndUpdate(org.mongodb.scala.model.Filters.equal("_id", objectId), unset("chromosome"))
      .toFuture
  }

  def findSymbolInDatabase(id: String): Future[Seq[Document]] = {

    val objId: ObjectId = new ObjectId(id)
//    val bsonObjectId = new BsonObjectId(objId)

    mongoCollection
      .find(org.mongodb.scala.model.Filters.equal("_id", objId))
      .toFuture
  }
}
