package com.spacecorpshandbook.ticker.core.chromosome

import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * Encoder trait for chromosome encoders
  */
trait Encoder {

  def mapMovingAverageBits(target: Ticker, history: Seq[Ticker], encodingMap: Seq[DayCalculatedBitEncoding]): Ticker
}
