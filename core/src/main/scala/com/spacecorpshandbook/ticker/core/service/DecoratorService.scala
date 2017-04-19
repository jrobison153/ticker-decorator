package com.spacecorpshandbook.ticker.core.service

import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeEncoder
import com.spacecorpshandbook.ticker.core.io.db.Persistence
import com.spacecorpshandbook.ticker.core.model.Ticker

import scala.concurrent.{ExecutionContext, Future}

/**
  * Provides high level orchestration of the process for updating a ticker
  */
class DecoratorService(persistence: Persistence,
                       chromosomeEncoder: ChromosomeEncoder) {

  implicit val ec = ExecutionContext.global

  /**
    * Update the provided ticker with a chromosome
    *
    * @param ticker
    * @return ticker updated with a chromosome
    */
  def addChromosome(ticker: Ticker): Future[Ticker] = {

    for {

      tickers <- persistence
        .symbol(ticker.ticker)
        .limit(10)
        .beforeDate(ticker.date)
        .search()
      updatedTicker <- {

        val decoratedTicker = chromosomeEncoder.mapFiveDaySmaCrossingTenDaySma(ticker, tickers)

        persistence replace decoratedTicker

        Future {
          decoratedTicker
        }

      }
    } yield {

      updatedTicker
    }
  }


}
