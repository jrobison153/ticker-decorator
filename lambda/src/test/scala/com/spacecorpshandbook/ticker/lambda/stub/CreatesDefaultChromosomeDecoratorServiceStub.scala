package com.spacecorpshandbook.ticker.lambda.stub

import com.spacecorpshandbook.ticker.core.chromosome.{ChromosomeDecoder, Encoder}
import com.spacecorpshandbook.ticker.core.io.db.Persistence
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.service.DecoratorService

import scala.concurrent.Future

class CreatesDefaultChromosomeDecoratorServiceStub(persistence: Persistence = null, chromosomeEncoder: Encoder = null)
  extends DecoratorService(persistence, chromosomeEncoder) {

  override def addChromosome(ticker: Ticker): Future[Ticker] = {

    Future {

      ticker.chromosome = ChromosomeDecoder.DEFAULT_CHROMOSOME

      ticker
    }
  }
}
