package com.spacecorpshandbook.ticker.core.calculator

import com.spacecorpshandbook.ticker.core.model.Ticker

import scala.math.BigDecimal.RoundingMode

/**
  * Calculate simple moving averages
  */
class SimpleMovingAverageCalculator extends MovingAverageCalculator {

  /**
    * Calculate a simple moving average for a specified number of days
    *
    * @param tickers - sequence of historical ticker data, assumes data in descending order based on date field
    * @param numberOfDays
    * @return
    */
  override def calculateForDays(tickers: Seq[Ticker], numberOfDays: Int): Option[BigDecimal] = {

    if (tickers.length >= numberOfDays) {
      val tickersToWorkOn = tickers.take(numberOfDays)

      val sumOfCloses = tickersToWorkOn.foldLeft(BigDecimal(0)) { (sum, ticker) =>

        sum + ticker.close
      }

      val result = sumOfCloses / numberOfDays

      Some(result.setScale(2, RoundingMode.DOWN))
    } else {

      None
    }
  }

  /**
    * Calculate a simple moving average for a specified number of days starting one day in the past
    *
    * @param tickers - sequence of historical ticker data, assumes data in descending order based on date field
    * @param numberOfDays
    * @return
    */
  override def calculatePreviousDayForDays(tickers: Seq[Ticker], numberOfDays: Int): Option[BigDecimal] = {

    if (tickers.nonEmpty && (tickers.tail.length >= numberOfDays)) {
      val tickersToWorkOn = tickers.tail.take(numberOfDays)

      val sumOfCloses = tickersToWorkOn.foldLeft(BigDecimal(0)) { (sum, ticker) =>

        sum + ticker.close
      }

      val result = sumOfCloses / numberOfDays

      Some(result.setScale(2, RoundingMode.DOWN))
    } else {

      None
    }
  }
}
