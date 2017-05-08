package com.spacecorpshandbook.ticker.core.service

import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeDecoder
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.spy.{EncoderSpy, PersistenceSpy}
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest._

import scala.concurrent.Future


class DecoratorServiceTest extends AsyncFlatSpec
  with Matchers
  with AsyncMockFactory
  with OneInstancePerTest {

  var decoratorService: DecoratorService = _
  var originalTicker: Ticker = _
  var persistenceSpy: PersistenceSpy = _
  var chromosomeEncoderSpy: EncoderSpy = _

  def setup(): Future[Ticker] = {

    originalTicker = new Ticker

    persistenceSpy = PersistenceSpy()

    chromosomeEncoderSpy = new EncoderSpy()

    decoratorService = new DecoratorService(persistenceSpy, chromosomeEncoderSpy)

    val tickerDecoratedFuture = decoratorService.addChromosome(originalTicker)

    tickerDecoratedFuture
  }

  behavior of "Decorator Service on a ticker that does not have a chromosome"

  it should "Map the Simple Moving Average crossing up bits" in {

    val setupDone = setup()

    setupDone map { _ =>

      chromosomeEncoderSpy.wasMovingAverageBitsCalled should equal(true)
      chromosomeEncoderSpy.encodingMapUsed should contain(ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP)

      succeed
    }
  }

  it should "Map the Simple Moving Average crossing down bits" in {

    val setupDone = setup()

    setupDone map { _ =>

      chromosomeEncoderSpy.wasMovingAverageBitsCalled should equal(true)
      chromosomeEncoderSpy.encodingMapUsed should contain(ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP)

      succeed
    }
  }

  it should "write the updated ticker to the database" in {

    val setupDone = setup()

    setupDone map { _ =>

      persistenceSpy.replacedTicker should equal(originalTicker)
      succeed
    }
  }
}