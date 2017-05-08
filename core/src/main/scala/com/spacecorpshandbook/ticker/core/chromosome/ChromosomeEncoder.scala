package com.spacecorpshandbook.ticker.core.chromosome

import com.spacecorpshandbook.ticker.core.calculator.MovingAverageCalculator
import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * Updates the various 'bits' of a chromosome
  *
  * @param movingAverageCalculator
  */
class ChromosomeEncoder(movingAverageCalculator: MovingAverageCalculator) extends Encoder {

  var targetTicker: Ticker = _

  var tickerHistory: Seq[Ticker] = _

  def mapMovingAverageBits(target: Ticker, history: Seq[Ticker], encodingMap: Seq[DayCalculatedBitEncoding]): Ticker = {

    targetTicker = target
    tickerHistory = history

    encodingMap.foreach(encoding => {

      updateMovingAverageBit(encoding)
    })

    targetTicker
  }

  private[this] def updateMovingAverageBit(encoding: DayCalculatedBitEncoding) = {

    val crossingMovingAverageValue = movingAverageCalculator
      .calculateForDays(tickerHistory, encoding.crossingMovingAverage)

    val previousMovingAverageValue = movingAverageCalculator
      .calculatePreviousDayForDays(tickerHistory, encoding.crossingMovingAverage)

    val movingAverageToCrossValue = movingAverageCalculator
      .calculateForDays(tickerHistory, encoding.numberOfDaysInCalculation)

    encoding match {
      case crossingUp: CrossingUpDayCalculatedBitEncoding => updateCrossingUpBit(encoding, crossingMovingAverageValue,
        previousMovingAverageValue, movingAverageToCrossValue)
      case crossingDown: CrossingDownDayCalculatedBitEncoding => updateCrossingDownBit(encoding, crossingMovingAverageValue,
        previousMovingAverageValue, movingAverageToCrossValue)
    }
  }

  private def updateCrossingUpBit(encoding: DayCalculatedBitEncoding, maybeCrossingMovingAverage: Option[BigDecimal],
                                  maybePreviousMovingAverageValue: Option[BigDecimal],
                                  maybeMovingAverageToCross: Option[BigDecimal]) = {

    (maybeCrossingMovingAverage, maybeMovingAverageToCross, maybePreviousMovingAverageValue) match {

      case (Some(crossingMovingAverageValue), Some(movingAverageToCrossValue), Some(previousMovingAverageValue)) =>

        if (isMovingAverageCrossingUp(crossingMovingAverageValue, movingAverageToCrossValue,
          previousMovingAverageValue)) {

          updateBit('1', encoding.bitIndex)
        }
        else {

          updateBit('0', encoding.bitIndex)
        }

      case _ => updateBit('0', encoding.bitIndex)
    }
  }

  private def updateCrossingDownBit(encoding: DayCalculatedBitEncoding, maybeCrossingMovingAverage: Option[BigDecimal],
                                    maybePreviousMovingAverageValue: Option[BigDecimal],
                                    maybeMovingAverageToCross: Option[BigDecimal]) = {

    (maybeCrossingMovingAverage, maybeMovingAverageToCross, maybePreviousMovingAverageValue) match {

      case (Some(crossingMovingAverageValue), Some(movingAverageToCrossValue), Some(previousMovingAverageValue)) =>

        if (isMovingAverageCrossingDown(crossingMovingAverageValue, movingAverageToCrossValue,
          previousMovingAverageValue)) {

          updateBit('1', encoding.bitIndex)
        }
        else {

          updateBit('0', encoding.bitIndex)
        }

      case _ => updateBit('0', encoding.bitIndex)
    }
  }

  private[this] def updateBit(newValueForIndex: Char, bitIndex: Int) = {

    targetTicker.chromosome = targetTicker.chromosome.substring(0, bitIndex) +
      newValueForIndex +
      targetTicker.chromosome.substring(bitIndex + 1)
  }

  private[this] def isMovingAverageCrossingUp(crossingMovingAverageValue: BigDecimal,
                                              movingAverageToCrossValue: BigDecimal,
                                              previousMovingAverageValue: BigDecimal): Boolean = {

    if ((previousMovingAverageValue < movingAverageToCrossValue)
      && (crossingMovingAverageValue >= movingAverageToCrossValue)) {

      true
    }
    else {

      false
    }
  }


  private[this] def isMovingAverageCrossingDown(crossingMovingAverageValue: BigDecimal,
                                                movingAverageToCrossValue: BigDecimal,
                                                previousMovingAverageValue: BigDecimal): Boolean = {

    if ((previousMovingAverageValue > movingAverageToCrossValue)
      && (crossingMovingAverageValue <= movingAverageToCrossValue)) {

      true
    }
    else {
      false
    }
  }
}
