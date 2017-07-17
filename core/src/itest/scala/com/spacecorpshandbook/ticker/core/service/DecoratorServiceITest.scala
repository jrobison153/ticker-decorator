package com.spacecorpshandbook.ticker.core.service

import com.spacecorpshandbook.ticker.core.calculator.SimpleMovingAverageCalculator
import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeEncoder
import com.spacecorpshandbook.ticker.core.io.db.{TickerDatabaseSetup, TickerPersistence}
import com.spacecorpshandbook.ticker.core.map.DocumentToTickerMap
import com.spacecorpshandbook.ticker.core.model.Ticker
import org.bson.BsonString
import org.bson.types.ObjectId
import org.mongodb.scala._
import org.mongodb.scala.bson.BsonValue
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Sorts.descending
import org.mongodb.scala.model.Updates.unset
import org.scalatest.{AsyncFlatSpec, BeforeAndAfter, BeforeAndAfterAll, Matchers}

import scala.concurrent.Future

class DecoratorServiceITest extends AsyncFlatSpec
  with Matchers
  with BeforeAndAfter
  with BeforeAndAfterAll
  with TickerDatabaseSetup {

  var decoratorService: DecoratorService = _

  override def beforeAll(): Unit = {

    super.beforeAll()

    databaseSetup
  }

  override def afterAll(): Unit = {

   databaseCleanup

    super.afterAll()
  }

  before {

    persistence = TickerPersistence(mongoDatabase)
  }

  behavior of "a DecoratorService on an brand new, un-decorated, ticker"

  it should "add a chromosome" in {

    for {

      complete <- initDoneFuture
      tickerDoc <- findSymbolWithYearOfHistoryInDatabase()
      tickerDocNoChromosome <- clearChromosomeFieldInDatabase(tickerDoc)
      updatedTicker <- addChromosomeToTicker(tickerDocNoChromosome)
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

  def addChromosomeToTicker(tickerDocNoChromosome: Document): Future[Ticker] = {

    val ticker: Ticker = DocumentToTickerMap.map(tickerDocNoChromosome)

    val movingAverageCalculator = new SimpleMovingAverageCalculator
    val chromosomeEncoder = new ChromosomeEncoder(movingAverageCalculator)
    val decoratorService = new DecoratorService(persistence, chromosomeEncoder)

    decoratorService addChromosome ticker
  }

  def findSymbolWithYearOfHistoryInDatabase() = {

    val findFuture = collection

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

    collection.findOneAndUpdate(org.mongodb.scala.model.Filters.equal("_id", objectId), unset("chromosome"))
      .toFuture
  }

  def findSymbolInDatabase(id: String): Future[Seq[Document]] = {

    val objId: ObjectId = new ObjectId(id)

    collection
      .find(org.mongodb.scala.model.Filters.equal("_id", objId))
      .toFuture
  }
}
