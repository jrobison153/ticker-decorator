package com.spacecorpshandbook.ticker.core.calculator

import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * Trait for moving average calculators
  */
trait MovingAverageCalculator {

  def calculateForDays(tickers: Seq[Ticker], numberOfDays: Int): Option[BigDecimal]

  def calculatePreviousDayForDays(tickers: Seq[Ticker], numberOfDays: Int): Option[BigDecimal]
}
