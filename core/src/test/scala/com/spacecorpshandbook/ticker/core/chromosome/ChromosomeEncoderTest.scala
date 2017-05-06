package com.spacecorpshandbook.ticker.core.chromosome

import com.spacecorpshandbook.ticker.core.calculator.SimpleMovingAverageCalculator
import com.spacecorpshandbook.ticker.core.constant.ChromosomeDecoder._
import com.spacecorpshandbook.ticker.core.model.Ticker
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

class ChromosomeEncoderTest extends FlatSpec
  with Matchers
  with MockFactory {

  trait Setup {

    var movingAverageCalculatorStub: SimpleMovingAverageCalculator = _
  }

  trait FiveDaySmaSetup extends Setup {

    movingAverageCalculatorStub = stub[SimpleMovingAverageCalculator]

    val ticker = new Ticker
    val history = Seq(new Ticker, new Ticker)
    var encoder: ChromosomeEncoder = _

    def setup(fiveDaySma: BigDecimal, smaBeingCrossedSma: BigDecimal, smaBeingCrossed: Int): Unit = {

      (movingAverageCalculatorStub.calculateForDays _).when(*, 5).returns(fiveDaySma)
      (movingAverageCalculatorStub.calculateForDays _).when(*, smaBeingCrossed).returns(smaBeingCrossedSma)

      encoder = new ChromosomeEncoder(movingAverageCalculatorStub)
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

    val smaToCross = 10
    val fasterSma = 6.0
    val slowerSma = 3.0

    setup(fasterSma, slowerSma, smaToCross)
    encoder.mapFiveDaySmaCrossingTenDaySma(ticker, history)

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP) should equal('1')
  }

  it should "Set the 5 day SMA crossing up over the 10 day SMA bit to 0 when it does not cross up" in new FiveDaySmaSetup {

    setBit(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP, '1')
    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP) should equal('1')

    val smaToCross = 10
    val fasterSma = 6.0
    val slowerSma = 3.0

    setup(slowerSma, fasterSma, smaToCross)
    encoder.mapFiveDaySmaCrossingTenDaySma(ticker, history)

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP) should equal('0')
  }

  it should "Set the 5 day SMA crossing up over the 20 day SMA bit to 1 when it does cross up" in new FiveDaySmaSetup {

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP) should equal('0')

    val smaToCross = 20
    val fasterSma = 6.0
    val slowerSma = 3.0

    setup(fasterSma, slowerSma, smaToCross)

    encoder.mapFiveDaySmaCrossingTwentyDaySma(ticker, history)

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP) should equal('1')
  }

  it should "Set the 5 day SMA crossing up over the 20 day SMA bit to 0 when it crosses down" in new FiveDaySmaSetup {

    setBit(FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP, '1')
    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP) should equal('1')

    val smaToCross = 20
    val slowerSma = 3.0
    val fasterSma = 6.0

    setup(slowerSma, fasterSma, smaToCross)

    encoder.mapFiveDaySmaCrossingTwentyDaySma(ticker, history)

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP) should equal('0')
  }

}
