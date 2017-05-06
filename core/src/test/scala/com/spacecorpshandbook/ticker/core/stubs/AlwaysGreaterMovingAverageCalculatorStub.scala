package com.spacecorpshandbook.ticker.core.stubs

import com.spacecorpshandbook.ticker.core.calculator.MovingAverageCalculator
import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * A moving average calculator stub that always returns a higher value for the desired
  * number of days.
  */
case class AlwaysGreaterMovingAverageCalculatorStub(numberOfDaysThatIsAlwaysGreatestMovingAverage: Int) extends MovingAverageCalculator {

  val GREATER_MOVING_AVERAGE: BigDecimal = 10

  val SMALLER_MOVING_AVERAGE: BigDecimal = 5

  override def calculateForDays(tickers: Seq[Ticker], numberOfDays: Int): BigDecimal = {

    if (numberOfDays == numberOfDaysThatIsAlwaysGreatestMovingAverage) {

      GREATER_MOVING_AVERAGE
    }
    else {

      SMALLER_MOVING_AVERAGE
    }
  }
}
