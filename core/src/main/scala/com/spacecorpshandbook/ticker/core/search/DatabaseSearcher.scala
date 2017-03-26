package com.spacecorpshandbook.ticker.core.search

import com.spacecorpshandbook.ticker.core.model.Ticker

import scala.concurrent.{ExecutionContext, Future}

/**
  * Provides higher order search functions over the database
  */
class DatabaseSearcher {

  def getHistoricalTickersFromDateLimitByDays(ticker: Ticker, numDaysToReturn: Int): Future[Seq[Ticker]] = {

    implicit val ec = ExecutionContext.global
//
//    // for 5day/10day SMA we need 10 days of historical data
//    val tickerCollection = MongoConnection.getDefaultDatabase.getCollection(STOCK_TICKER_COLLECTION)
//    val filterTickerOnDate: Bson = and(equal("ticker", ticker.ticker), lte("date", ticker.date))
//
//    tickerCollection.find(filterTickerOnDate).limit(10).toFuture

    val tickerFuture: Future[Seq[Ticker]] = Future {

      var tickers = Seq(new Ticker())

      tickers
    }

    tickerFuture
  }

}
