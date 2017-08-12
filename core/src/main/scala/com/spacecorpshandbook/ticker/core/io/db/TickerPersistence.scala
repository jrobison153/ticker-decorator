package com.spacecorpshandbook.ticker.core.io.db

import java.time.LocalDate

import com.spacecorpshandbook.ticker.core.constant.Database._
import com.spacecorpshandbook.ticker.core.map.{DocumentToTickerMap, TickerToDocumentMap}
import com.spacecorpshandbook.ticker.core.model.Ticker
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.{and, equal, lte}
import org.mongodb.scala.model.Sorts.descending
import org.mongodb.scala.result.UpdateResult
import org.mongodb.scala.{Document, MongoCollection, MongoDatabase}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Provides higher order search functions over the database
  *
  */
class TickerPersistence(private var dbConnection: MongoDatabase) extends Persistence {

  /*
  TODO: replace method doesn't seem to fit, possibly refactor out
   */

  private var id: String = ""
  private var limitDays: Int = 365
  private var tickersBeforeDate: LocalDate = _
  private var tickerSymbol: String = ""

  def search(): Future[Seq[Ticker]] = {

    implicit val ec = ExecutionContext.global

    val tickerCollection: MongoCollection[Document] = MongoConnection
      .getDefaultDatabase
      .getCollection(STOCK_TICKER_COLLECTION)

    val findFuture = buildFilter match {

      case Some(filter) => tickerCollection
        .find(filter)
        .limit(limitDays)
        .sort(descending("date"))
        .toFuture
      case None => tickerCollection
        .find()
        .limit(limitDays)
        .sort(descending("date"))
        .toFuture
    }

    findFuture onFailure {
      case e => System.err.println(s"""Search for ticker failed: ${e.toString}""")
    }

    val mappedTickerFuture = findFuture map { documents =>

      documents map { document =>

        DocumentToTickerMap map document
      }
    }

    mappedTickerFuture onFailure {

      case e => System.err.println(s"""Failed to map documents to tickers:git st ${e.toString}""")
    }

    mappedTickerFuture
  }

  def replace(ticker: Ticker): Future[UpdateResult] = {

    val tickerCollection: MongoCollection[Document] = MongoConnection
      .getDefaultDatabase
      .getCollection(STOCK_TICKER_COLLECTION)

    implicit val ec = ExecutionContext.global

    val tickerAsDocument = TickerToDocumentMap.map(ticker)
    val objId = new ObjectId(ticker.id)

    val updateFuture = tickerCollection.replaceOne(equal("_id", objId), tickerAsDocument).toFuture

    updateFuture map { result =>

      result
    }
  }

  def symbol(tickerSymbol: String): TickerPersistence = {

    this.tickerSymbol = tickerSymbol

    this
  }

  def beforeDate(date: LocalDate): TickerPersistence = {

    this.tickersBeforeDate = date

    this
  }

  def limit(numDays: Int): TickerPersistence = {

    this.limitDays = numDays

    this
  }

  def id(id: String): TickerPersistence = {

    this.id = id

    this
  }

  private def buildFilter: Option[Bson] = {

    var filtersToApply: Seq[Bson] = Seq()

    if (tickersBeforeDate != null) {

      filtersToApply = filtersToApply :+ lte("date", tickersBeforeDate.toString)
    }

    if (!tickerSymbol.isEmpty) {

      filtersToApply = filtersToApply :+ equal("ticker", tickerSymbol)
    }

    if (filtersToApply.nonEmpty) {

      val filter: Bson = and(filtersToApply: _*)

      Some(filter)
    }
    else {

      None
    }

  }
}

object TickerPersistence {

  def apply(dbConnection: MongoDatabase): TickerPersistence = {

    new TickerPersistence(dbConnection)
  }
}
