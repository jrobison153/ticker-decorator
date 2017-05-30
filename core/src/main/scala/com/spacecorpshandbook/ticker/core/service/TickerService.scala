package com.spacecorpshandbook.ticker.core.service

import com.spacecorpshandbook.ticker.core.model.Ticker

import scala.concurrent.Future

trait TickerService {

  def addChromosome(ticker: Ticker): Future[Ticker]
}
