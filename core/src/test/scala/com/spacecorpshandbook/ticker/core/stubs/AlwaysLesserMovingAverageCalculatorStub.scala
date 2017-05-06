package com.spacecorpshandbook.ticker.core.stubs

import com.spacecorpshandbook.ticker.core.calculator.MovingAverageCalculator
import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * Created by justin on 5/6/17.
  */
case class AlwaysLesserMovingAverageCalculatorStub(numberOfDaysThatIsAlwaysLessorMovingAverage: Int) extends MovingAverageCalculator {

  val LESSER_MOVING_AVERAGE: BigDecimal = 5

  val GREATER_MOVING_AVERAGE: BigDecimal = 10

  override def calculateForDays(tickers: Seq[Ticker], numberOfDays: Int): BigDecimal = {

    if (numberOfDays == numberOfDaysThatIsAlwaysLessorMovingAverage) {

      LESSER_MOVING_AVERAGE
    }
    else {

      GREATER_MOVING_AVERAGE
    }
  }
}
