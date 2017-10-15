package com.spacecorpshandbook.ticker.spring.stub

import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.service.TickerService

import scala.concurrent.Future

class ExceptionThrowingDecoratorServiceStub extends TickerService {

  override def addChromosome(ticker: Ticker): Future[Ticker] = {

    Future.failed(new RuntimeException)
  }
}
