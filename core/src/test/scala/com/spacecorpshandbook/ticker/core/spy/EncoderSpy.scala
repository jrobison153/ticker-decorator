package com.spacecorpshandbook.ticker.core.spy

import com.spacecorpshandbook.ticker.core.chromosome.{DayCalculatedBitEncoding, Encoder}
import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * Basic Encoder spy
  */
class EncoderSpy extends Encoder {

  var wasMovingAverageBitsCalled: Boolean = false

  var encodingMapUsed: Seq[Seq[DayCalculatedBitEncoding]] = Seq()

  override def mapMovingAverageBits(target: Ticker, history: Seq[Ticker],
                                    encodingMap: Seq[DayCalculatedBitEncoding]): Ticker = {

    wasMovingAverageBitsCalled = true

    encodingMapUsed = encodingMapUsed :+ encodingMap

    target
  }
}
