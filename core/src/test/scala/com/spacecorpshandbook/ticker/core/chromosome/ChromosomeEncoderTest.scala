package com.spacecorpshandbook.ticker.core.chromosome

import com.spacecorpshandbook.ticker.core.constant.ChromosomeDecoder._
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.stubs.{AlwaysGreaterMovingAverageCalculatorStub, AlwaysLesserMovingAverageCalculatorStub}
import org.scalatest.{FlatSpec, Matchers}

class ChromosomeEncoderTest extends FlatSpec
  with Matchers {

  trait FiveDaySmaSetup {

    val ticker = new Ticker
    val history = Seq(new Ticker, new Ticker)
    var encoder: ChromosomeEncoder = _

    def setupForAlwaysCrossingUp(numberOfDays: Int): Unit = {

      encoder = new ChromosomeEncoder(AlwaysGreaterMovingAverageCalculatorStub(numberOfDays))
    }

    def setupForAlwaysCrossingDown(numberOfDays: Int): Unit = {

      encoder = new ChromosomeEncoder(AlwaysLesserMovingAverageCalculatorStub(numberOfDays))
    }

    def setBit(bitIndex: Int, newValueForIndex: Char): Unit = {

      ticker.chromosome = ticker.chromosome.substring(0, bitIndex) +
        newValueForIndex +
        ticker.chromosome.substring(bitIndex + 1)
    }
  }

  behavior of "A chromosome encoder"

  it should "Set the 5 day SMA crossing up over the 10 day SMA bit to 1 when it does cross up" in new FiveDaySmaSetup {

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP) should equal('0')

    setupForAlwaysCrossingUp(5)
    encoder.mapFiveDaySmaCrossingTenDaySma(ticker, history)

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP) should equal('1')
  }

  it should "Set the 5 day SMA crossing up over the 10 day SMA bit to 0 when it does not cross up" in new FiveDaySmaSetup {

    setBit(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP, '1')
    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP) should equal('1')

    setupForAlwaysCrossingDown(5)
    encoder.mapFiveDaySmaCrossingTenDaySma(ticker, history)

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP) should equal('0')
  }

  it should "Set the 5 day SMA crossing up over the 20 day SMA bit to 1 when it does cross up" in new FiveDaySmaSetup {

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP) should equal('0')

    setupForAlwaysCrossingUp(5)
    encoder.mapFiveDaySmaCrossingTwentyDaySma(ticker, history)

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP) should equal('1')
  }

  it should "Set the 5 day SMA crossing up over the 20 day SMA bit to 0 when it crosses down" in new FiveDaySmaSetup {

    setBit(FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP, '1')
    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP) should equal('1')

    setupForAlwaysCrossingDown(5)
    encoder.mapFiveDaySmaCrossingTwentyDaySma(ticker, history)

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP) should equal('0')
  }

}
