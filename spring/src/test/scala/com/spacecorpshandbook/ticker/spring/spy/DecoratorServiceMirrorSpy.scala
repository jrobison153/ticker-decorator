package com.spacecorpshandbook.ticker.spring.spy

import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.spring.wrapper.TickerServiceMirror

import scala.concurrent.{ExecutionContext, Future}

class DecoratorServiceMirrorSpy extends TickerServiceMirror {

  implicit val ec = ExecutionContext.global

  var addChromosomeCalled: Boolean = false

  var tickerPassedForDecoration: Ticker = _

  override def addChromosome(ticker: Ticker): Future[Ticker] = {

    addChromosomeCalled = true
    tickerPassedForDecoration = ticker

    Future {
      val decoratedTicker = new Ticker

      decoratedTicker.chromosome = "10101010001010101"

      decoratedTicker
    }
  }
}
