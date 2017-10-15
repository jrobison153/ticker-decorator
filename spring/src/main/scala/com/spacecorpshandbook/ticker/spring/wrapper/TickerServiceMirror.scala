package com.spacecorpshandbook.ticker.spring.wrapper

import com.spacecorpshandbook.ticker.core.model.Ticker

import scala.concurrent.Future

/**
  * mirrors the real TickerService trait allowing Spring to create the correct beans w/o
  * cyclical dependencies
  */
trait TickerServiceMirror {

  def addChromosome(ticker: Ticker): Future[Ticker]
}
