package com.spacecorpshandbook.ticker.core.service

import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeEncoder
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.search.DatabaseSearcher
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest._

import scala.concurrent.Future


class DecoratorServiceTest extends AsyncFlatSpec
  with Matchers
  with AsyncMockFactory
  with OneInstancePerTest {

  var decoratorService: DecoratorService = _
  var ticker: Ticker = _
  var updatedTicker: Ticker = _
  var databaseSearcherStub: DatabaseSearcher = _
  var chromosomeEncoderStub: ChromosomeEncoder = _

  val tickerA = new Ticker()
  val tickerB = new Ticker()

  val dummyTickerSeq = Seq(tickerA, tickerB)

  def setup(fiveDaySma: BigDecimal = 6.0, tenDaySma: BigDecimal = 3.0): Future[Ticker] = {

    val resolvedFuture: Future[Seq[Ticker]] = Future {

      dummyTickerSeq
    }

    databaseSearcherStub = stub[DatabaseSearcher]
    (databaseSearcherStub.getHistoricalTickersFromDateLimitByDays _).when(*, *).returns(resolvedFuture)

    chromosomeEncoderStub = stub[ChromosomeEncoder]

    decoratorService = new DecoratorService(databaseSearcherStub, chromosomeEncoderStub)

    ticker = new Ticker

    val tickerDecoratedFuture = decoratorService.addChromosome(ticker)

    tickerDecoratedFuture
  }

  behavior of "Decorator Service on a ticker that does not have a chromosome"

  it should "Map the the 5 day SMA crossing 10 day SMA bit of the chromosome" in {

    val setupDone: Future[Ticker] = setup()

    setupDone map {ticker =>

      (chromosomeEncoderStub.mapFiveDaySmaCrossingTenDaySma _).verify(dummyTickerSeq)
      succeed
    }
  }
}