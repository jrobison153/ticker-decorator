package com.spacecorpshandbook.ticker.core.service

import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeEncoder
import com.spacecorpshandbook.ticker.core.io.db.Persistence
import com.spacecorpshandbook.ticker.core.model.Ticker
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest._

import scala.concurrent.Future


class DecoratorServiceTest extends AsyncFlatSpec
  with Matchers
  with AsyncMockFactory
  with OneInstancePerTest {

  var decoratorService: DecoratorService = _
  var originalTicker: Ticker = _
  val decoratedTicker: Ticker = new Ticker
  var updatedTicker: Ticker = _
  var persistenceStub: Persistence = _
  var chromosomeEncoderStub: ChromosomeEncoder = _

  val tickerA = new Ticker()
  val tickerB = new Ticker()

  val dummyTickerSeq = Seq(tickerA, tickerB)

  def setup(): Future[Ticker] = {

    val searchFuture: Future[Seq[Ticker]] = Future {

      dummyTickerSeq
    }

    updatedTicker = new Ticker
    val updateFuture: Future[Ticker] = Future {

      updatedTicker
    }

    originalTicker = new Ticker

    persistenceStub = stub[Persistence]
    (persistenceStub.beforeDate _).when(*).returns(persistenceStub)
    (persistenceStub.symbol _).when(*).returns(persistenceStub)
    (persistenceStub.limit _).when(*).returns(persistenceStub)
    (persistenceStub.search _).when().returns(searchFuture)

    chromosomeEncoderStub = stub[ChromosomeEncoder]
    (chromosomeEncoderStub.mapFiveDaySmaCrossingTenDaySma _).when(*, *).returns(decoratedTicker)

    decoratorService = new DecoratorService(persistenceStub, chromosomeEncoderStub)

    val tickerDecoratedFuture = decoratorService.addChromosome(originalTicker)

    tickerDecoratedFuture
  }

  behavior of "Decorator Service on a ticker that does not have a chromosome"

  it should "Map the the 5 day SMA crossing 10 day SMA bit of the chromosome" in {

    val setupDone: Future[Ticker] = setup()

    setupDone map { _ =>

      (chromosomeEncoderStub.mapFiveDaySmaCrossingTenDaySma _).verify(originalTicker, dummyTickerSeq)
      succeed
    }
  }

  it should "write the updated ticker to the database" in {

    val setupDone = setup()

    setupDone map { _ =>

      (persistenceStub.replace _).verify(decoratedTicker)
      succeed
    }
  }

}