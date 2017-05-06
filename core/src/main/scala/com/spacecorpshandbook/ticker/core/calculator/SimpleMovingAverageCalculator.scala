package com.spacecorpshandbook.ticker.core.calculator

import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * Calculate simple moving averages
  */
class SimpleMovingAverageCalculator extends MovingAverageCalculator {

  def calculateForDays(tickers: Seq[Ticker], numberOfDays: Int) : BigDecimal = {

    BigDecimal(0)
  }
}
