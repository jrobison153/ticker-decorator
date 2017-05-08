package com.spacecorpshandbook.ticker.core.stub

import com.spacecorpshandbook.ticker.core.calculator.MovingAverageCalculator
import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * Created by justin on 5/19/17.
  */
case class InsufficientDataMovingAverageCalculatorStub(movingAverage: Int) extends MovingAverageCalculator {

  override def calculateForDays(tickers: Seq[Ticker], numberOfDays: Int): Option[BigDecimal] = {

    if(numberOfDays == movingAverage) {

      None
    } else {

      Some(10)
    }
  }

  override def calculatePreviousDayForDays(tickers: Seq[Ticker], numberOfDays: Int): Option[BigDecimal] = {

    Some(10)
  }
}
