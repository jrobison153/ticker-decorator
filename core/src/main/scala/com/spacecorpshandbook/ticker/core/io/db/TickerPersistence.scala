package com.spacecorpshandbook.ticker.core.io.db

import java.time.{LocalDateTime, ZoneOffset}
import java.util.Date

import com.spacecorpshandbook.ticker.core.constant.Database._
import com.spacecorpshandbook.ticker.core.map.BsonToBsonMappable
import com.spacecorpshandbook.ticker.core.model.Ticker
import org.bson.conversions.Bson
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.{MongoCollection, MongoDatabase}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Provides higher order search functions over the database
  */
class TickerPersistence(private var dbConnection: MongoDatabase) extends Persistence {


  private var id: String = ""
  private var limitDays: Int = 365
  private var tickersBeforeDate: LocalDateTime = _
  private var tickerSymbol: String = ""

  def search(): Future[Seq[Ticker]] = {

    implicit val ec = ExecutionContext.global

    val tickerCollection: MongoCollection[Ticker] = MongoConnection
      .getDefaultDatabase
      .getCollection(STOCK_TICKER_COLLECTION)

    val filter: Bson = buildFilter

    val findFuture = tickerCollection
      .find(filter)
      .limit(limitDays)
      .sort(descending("date"))
      .toFuture

    findFuture map { documents =>

      documents map { document =>

        val ticker = new Ticker
        BsonToBsonMappable.map(document, ticker)
        ticker
      }
    }
  }

  def buildFilter: Bson = {

    var filtersToApply: Seq[Bson] = Seq()

    if(tickersBeforeDate != null) {

      val searchDate = Date.from(tickersBeforeDate.toInstant(ZoneOffset.UTC))
      filtersToApply = filtersToApply :+ lte("date", searchDate)
    }

    if (!tickerSymbol.isEmpty) {

      filtersToApply = filtersToApply :+ equal("ticker", tickerSymbol)
    }

    val filter: Bson = and(filtersToApply: _*)

    filter
  }

  def symbol(tickerSymbol: String): TickerPersistence = {

    this.tickerSymbol = tickerSymbol

    this
  }

  def beforeDate(date: LocalDateTime): TickerPersistence = {

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

  def update(ticker: Ticker) : Future[Ticker] = {
//
//    val tickerCollection: MongoCollection[Ticker] = MongoConnection
//      .getDefaultDatabase
//      .getCollection(STOCK_TICKER_COLLECTION)
//
//    val filter: Bson = buildFilter
//
//    tickerCollection.updateOne(filter, )

    implicit val ec = ExecutionContext.global

    Future {

      new Ticker
    }
  }

}

object TickerPersistence {

  def apply(dbConnection: MongoDatabase): TickerPersistence = {

    new TickerPersistence(dbConnection)
  }
}
