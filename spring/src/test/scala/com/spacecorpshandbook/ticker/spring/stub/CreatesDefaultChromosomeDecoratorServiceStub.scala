package com.spacecorpshandbook.ticker.spring.stub

import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeDecoder
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.spring.wrapper.TickerServiceMirror

import scala.concurrent.{ExecutionContext, Future}

class CreatesDefaultChromosomeDecoratorServiceStub extends TickerServiceMirror {

  implicit val ec = ExecutionContext.global

  override def addChromosome(ticker: Ticker): Future[Ticker] = {

    Future {

      ticker.chromosome = ChromosomeDecoder.DEFAULT_CHROMOSOME

      ticker
    }
  }
}
