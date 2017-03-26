package com.spacecorpshandbook.ticker.core.service

import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeEncoder
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.search.DatabaseSearcher

import scala.concurrent.{ExecutionContext, Future}

/**
  * Provides high level orchestration of the process for updating a ticker
  */
class DecoratorService(dbSearcher: DatabaseSearcher,
                            chromosomeEncoder: ChromosomeEncoder) {

  implicit val ec = ExecutionContext.global

  /**
    * Update the provided ticker with a chromosome
    *
    * @param ticker
    * @return ticker updated with a chromosome
    */
  def addChromosome(ticker: Ticker): Future[Ticker] = {

    val findFuture = dbSearcher.getHistoricalTickersFromDateLimitByDays(ticker, 10)

    findFuture map { tickers =>

      chromosomeEncoder.mapFiveDaySmaCrossingTenDaySma(tickers)

      // update/insert updated ticker back into database

      ticker
    }
  }


}
