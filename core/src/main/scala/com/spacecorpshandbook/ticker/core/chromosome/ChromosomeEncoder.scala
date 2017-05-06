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

  var tickerHistory: Seq[Ticker] = _

  var bitToUpdate: Int = _

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
    tickerHistory = history
    bitToUpdate = FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP

    updateMovingAverageBit(5, 10)

    targetTicker
  }


  def mapFiveDaySmaCrossingTwentyDaySma(target: Ticker, history: Seq[Ticker]): Ticker = {

    targetTicker = target
    tickerHistory = history
    bitToUpdate = FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP

    updateMovingAverageBit(5, 20)

    targetTicker
  }

  private[this] def updateMovingAverageBit(subjectMovingAverage: Int, movingAverageToCross: Int) = {

    val subjectMovingAverageValue = movingAverageCalculator.calculateForDays(tickerHistory, subjectMovingAverage)
    val movingAverageToCrossValue = movingAverageCalculator.calculateForDays(tickerHistory, movingAverageToCross)

    if (subjectMovingAverageValue >= movingAverageToCrossValue) {

      updateBit('1')
    }
    else {

      updateBit('0')
    }
  }

  private[this] def updateBit(newValueForIndex: Char) = {

    targetTicker.chromosome = targetTicker.chromosome.substring(0, bitToUpdate) +
      newValueForIndex +
      targetTicker.chromosome.substring(bitToUpdate + 1)
  }
}
