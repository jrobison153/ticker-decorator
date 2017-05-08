package com.spacecorpshandbook.ticker.core.stub

import com.spacecorpshandbook.ticker.core.calculator.MovingAverageCalculator
import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * A moving average calculator stub that always returns a equal values for the moving average that is crossing
  * and the moving average to be crossed. e.g. the 5 day SMA and the 10 day SMA have the same value
  */
case class AlwaysCrossingDownEqualMovingAverageCalculatorStub(numberOfDaysThatIsAlwaysCrossingAnotherMovingAverage: Int)
  extends MovingAverageCalculator {

  val CURRENT_MOVING_AVERAGE_THAT_IS_CROSSING: BigDecimal = 10

  val CURRENT_MOVING_AVERAGE_BEING_CROSSED: BigDecimal = 10

  val PREVIOUS_MOVING_AVERAGE_THAT_IS_CROSSING: BigDecimal = CURRENT_MOVING_AVERAGE_BEING_CROSSED + 1

  override def calculateForDays(tickers: Seq[Ticker], numberOfDays: Int): Option[BigDecimal] = {

    if (numberOfDays == numberOfDaysThatIsAlwaysCrossingAnotherMovingAverage) {

      Some(CURRENT_MOVING_AVERAGE_THAT_IS_CROSSING)
    }
    else {

      Some(CURRENT_MOVING_AVERAGE_BEING_CROSSED)
    }
  }

  override def calculatePreviousDayForDays(tickers: Seq[Ticker], numberOfDays: Int): Option[BigDecimal] = {

    Some(PREVIOUS_MOVING_AVERAGE_THAT_IS_CROSSING)
  }
}
