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

    def setup(fasterSma: BigDecimal, slowerSma: BigDecimal): Unit = {

      (movingAverageCalculatorStub.calculateForDays _).when(*, 5).returns(fasterSma)
      (movingAverageCalculatorStub.calculateForDays _).when(*, 10).returns(slowerSma)

      val encoder = new ChromosomeEncoder(movingAverageCalculatorStub)
      encoder.mapFiveDaySmaCrossingTenDaySma(ticker, history)
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

    setup(6.0, 3.0)

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP) should equal('1')
  }

  it should "Set the 5 day SMA crossing up over the 10 day SMA bit to 0 when it does not cross up" in new FiveDaySmaSetup {

    setBit(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP, '1')
    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP) should equal('1')

    setup(3.0, 6.0)

    ticker.chromosome(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP) should equal('0')
  }
}
