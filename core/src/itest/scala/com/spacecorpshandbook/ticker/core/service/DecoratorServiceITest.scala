package com.spacecorpshandbook.ticker.core.service

import org.mongodb.scala._
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
  var mongoClient: MongoClient = _

  before {

    mongoClient = MongoClient("mongodb://localhost")
  }

  behavior of "a DecoratorService on an brand new, un-decorated, ticker"

  it should "add a chromosome" in {

    val futureDocuments: Future[Seq[Document]] = mongoClient.getDatabase("testStockData")
      .getCollection("tickers")
      .find(org.mongodb.scala.model.Filters.equal("ticker", "A"))
      .first.toFuture

    futureDocuments map { documents =>

      val tickerDoc = documents.head

      println(tickerDoc)

      documents.length should equal(0)
    }

  }
}
