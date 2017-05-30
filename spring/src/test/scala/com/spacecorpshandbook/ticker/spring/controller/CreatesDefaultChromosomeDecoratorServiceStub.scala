package com.spacecorpshandbook.ticker.spring.controller

import com.spacecorpshandbook.ticker.core.chromosome.{ChromosomeDecoder, Encoder}
import com.spacecorpshandbook.ticker.core.io.db.Persistence
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.service.DecoratorService

import scala.concurrent.Future

class CreatesDefaultChromosomeDecoratorServiceStub(persistence: Persistence, chromosomeEncoder: Encoder)
  extends DecoratorService(persistence, chromosomeEncoder) {

  override def addChromosome(ticker: Ticker): Future[Ticker] = {

    Future {

      ticker.chromosome = ChromosomeDecoder.DEFAULT_CHROMOSOME

      ticker
    }
  }
}
