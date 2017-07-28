package com.spacecorpshandbook.ticker.spring.spy

import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.service.TickerService

import scala.concurrent.{ExecutionContext, Future}

class DecoratorServiceSpy() extends TickerService {

  implicit val ec = ExecutionContext.global

  var addChromosomeCalled: Boolean = false

  var tickerPassedForDecoration: Ticker = _

  override def addChromosome(ticker: Ticker): Future[Ticker] = {

    addChromosomeCalled = true
    tickerPassedForDecoration = ticker

    Future {
      null
    }
  }
}
