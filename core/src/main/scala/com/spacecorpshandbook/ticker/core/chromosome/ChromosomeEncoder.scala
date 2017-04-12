package com.spacecorpshandbook.ticker.core.chromosome

import com.spacecorpshandbook.ticker.core.calculator.SimpleMovingAverageCalculator
import com.spacecorpshandbook.ticker.core.constant.ChromosomeDecoder._
import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * Updates the various 'bits' of a chromosome
  *
  * @param movingAverageCalculator
  */
class ChromosomeEncoder(movingAverageCalculator: SimpleMovingAverageCalculator) {

  var targetTicker: Ticker = _

  /**
    *
    * Set the 5 day SMA crossed 10 day SMA bit based on the historical performance of this equity
    *
    * @param target  - ticker who's chromosome should be updated
    * @param history - historical ticker data to be used to calculate chromosome values
    * @return the updated ticker
    */
  def mapFiveDaySmaCrossingTenDaySma(target: Ticker, history: Seq[Ticker]): Ticker = {

    targetTicker = target

    updateFiveDayMovingAverageBit(history)

    targetTicker
  }

  private[this] def updateFiveDayMovingAverageBit(tickerHistory: Seq[Ticker]) = {

    val fiveDaySimpleMovingAverage = movingAverageCalculator.calculateForDays(tickerHistory, 5)
    val tenDaySimpleMovingAverage = movingAverageCalculator.calculateForDays(tickerHistory, 10)

    if (fiveDaySimpleMovingAverage >= tenDaySimpleMovingAverage) {

      updateBit(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP, '1')
    }
    else {

      updateBit(FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP, '0')
    }
  }

  private[this] def updateBit(bitIndex: Integer, newValueForIndex: Char) = {

    targetTicker.chromosome = targetTicker.chromosome.substring(0, bitIndex) +
      newValueForIndex +
      targetTicker.chromosome.substring(bitIndex + 1)
  }
}
