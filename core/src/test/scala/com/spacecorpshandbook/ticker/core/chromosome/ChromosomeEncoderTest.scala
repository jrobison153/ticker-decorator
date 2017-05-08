package com.spacecorpshandbook.ticker.core.chromosome

import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.stub._
import org.scalatest.{FlatSpec, Matchers}

class ChromosomeEncoderTest extends FlatSpec
  with Matchers {

  trait SmaSetup {

    var bitToEncode: DayCalculatedBitEncoding = _
    var encoder: ChromosomeEncoder = _
    var encodingMap: Seq[DayCalculatedBitEncoding] = _
    val ticker = new Ticker
    val history = Seq(new Ticker, new Ticker)

    def setBit(bitIndex: Int, newValueForIndex: Char): Unit = {

      ticker.chromosome = ticker.chromosome.substring(0, bitIndex) +
        newValueForIndex +
        ticker.chromosome.substring(bitIndex + 1)
    }

    def setAllChromosomeBitsTo(bitValue: Char) = encodingMap.foreach(encoding => setBit(encoding.bitIndex, bitValue))
  }

  trait SmaCrossingUpSetup extends SmaSetup {

    bitToEncode = CrossingUpDayCalculatedBitEncoding(0, 5, 10)
    encodingMap = Seq(bitToEncode)
  }

  trait CrossUpSetup extends SmaCrossingUpSetup {

    encoder = new ChromosomeEncoder(AlwaysCrossingUpMovingAverageCalculatorStub(bitToEncode.crossingMovingAverage))

    setAllChromosomeBitsTo('0')
  }

  trait CrossUpEqualSetup extends SmaCrossingUpSetup {

    encoder = new ChromosomeEncoder(AlwaysCrossingUpEqualMovingAverageCalculatorStub(bitToEncode.crossingMovingAverage))

    setAllChromosomeBitsTo('0')
  }

  trait AlreadyCrossedUpSetup extends SmaCrossingUpSetup {

    encoder = new ChromosomeEncoder(PreviouslyCrossedUpMovingAverageCalculatorStub(bitToEncode.crossingMovingAverage))

    setAllChromosomeBitsTo('1')
  }

  trait SmaCrossDownSetup extends SmaSetup {

    bitToEncode = CrossingDownDayCalculatedBitEncoding(0, 5, 10)
    encodingMap = Seq(bitToEncode)
  }

  trait CrossDownSetup extends SmaCrossDownSetup {

    encoder = new ChromosomeEncoder(AlwaysCrossingDownMovingAverageCalculatorStub(bitToEncode.crossingMovingAverage))

    setAllChromosomeBitsTo('0')
  }

  trait CrossDownEqualSetup extends SmaCrossDownSetup {

    encoder = new ChromosomeEncoder(AlwaysCrossingDownEqualMovingAverageCalculatorStub(bitToEncode.crossingMovingAverage))

    setAllChromosomeBitsTo('0')
  }

  trait AlreadyCrossedDownSetup extends SmaCrossDownSetup {

    encoder = new ChromosomeEncoder(PreviouslyCrossedDownMovingAverageCalculatorStub(bitToEncode.crossingMovingAverage))

    setAllChromosomeBitsTo('1')
  }

  abstract class InsufficientCurrentMovingAverageDataSetup(aBitToEncode: DayCalculatedBitEncoding) extends SmaSetup {

    bitToEncode = aBitToEncode
    encodingMap = Seq(bitToEncode)

    encoder = new ChromosomeEncoder(InsufficientDataMovingAverageCalculatorStub(bitToEncode.crossingMovingAverage))

    setAllChromosomeBitsTo('1')
  }

  abstract class InsufficientMovingAverageToCrossDataSetup(aBitToEncode: DayCalculatedBitEncoding) extends SmaSetup {

    bitToEncode = aBitToEncode
    encodingMap = Seq(bitToEncode)

    encoder = new ChromosomeEncoder(InsufficientDataMovingAverageCalculatorStub(bitToEncode.numberOfDaysInCalculation))

    setAllChromosomeBitsTo('1')
  }

  abstract class InsufficientPreviousDayMovingAverageDataSetup(aBitToEncode: DayCalculatedBitEncoding) extends SmaSetup {

    bitToEncode = aBitToEncode
    encodingMap = Seq(bitToEncode)

    encoder = new ChromosomeEncoder(InsufficientDataPreviousDayMovingAverageCalculatorStub(bitToEncode.numberOfDaysInCalculation))

    setAllChromosomeBitsTo('1')
  }

  behavior of "A chromosome encoder when mapping moving average crossing up bits"

  it should "Set the SMA crossing up bit to 1 when the previous value is below the MA to cross and the current value is above it" in new CrossUpSetup {

    ticker.chromosome(bitToEncode.bitIndex) should equal('0')

    encoder.mapMovingAverageBits(ticker, history, encodingMap)

    ticker.chromosome(bitToEncode.bitIndex) should equal('1')
  }

  it should "Set the SMA crossing up bit to 1 when the previous value is below the MA to cross and the current value is equal it" in new CrossUpEqualSetup {

    ticker.chromosome(bitToEncode.bitIndex) should equal('0')

    encoder.mapMovingAverageBits(ticker, history, encodingMap)

    ticker.chromosome(bitToEncode.bitIndex) should equal('1')
  }

  it should "Set the SMA crossing up bit to 0 when the previous value is above the MA to cross" in new AlreadyCrossedUpSetup {

    ticker.chromosome(bitToEncode.bitIndex) should equal('1')

    encoder.mapMovingAverageBits(ticker, history, encodingMap)

    ticker.chromosome(bitToEncode.bitIndex) should equal('0')
  }

  it should "Set the SMA crossing up bit to 0 when there aren't enough days of data to calculate the current value" in
    new InsufficientCurrentMovingAverageDataSetup(CrossingUpDayCalculatedBitEncoding(0, 5, 10)) {

      ticker.chromosome(bitToEncode.bitIndex) should equal('1')

      encoder.mapMovingAverageBits(ticker, history, encodingMap)

      ticker.chromosome(bitToEncode.bitIndex) should equal('0')
    }

  it should "Set the SMA crossing up bit to 0 when there aren't enough days of data to calculate the MA to cross" in
    new InsufficientMovingAverageToCrossDataSetup(CrossingUpDayCalculatedBitEncoding(0, 5, 10)) {

      ticker.chromosome(bitToEncode.bitIndex) should equal('1')

      encoder.mapMovingAverageBits(ticker, history, encodingMap)

      ticker.chromosome(bitToEncode.bitIndex) should equal('0')
    }

  it should "Set the SMA crossing up bit to 0 when there aren't enough days of data to calculate the previous day MA" in
    new InsufficientPreviousDayMovingAverageDataSetup(CrossingUpDayCalculatedBitEncoding(0, 5, 10)) {

      ticker.chromosome(bitToEncode.bitIndex) should equal('1')

      encoder.mapMovingAverageBits(ticker, history, encodingMap)

      ticker.chromosome(bitToEncode.bitIndex) should equal('0')
    }

  behavior of "A chromosome encoder when mapping moving average crossing down bits"

  it should "Set the SMA crossing down bit to 1 when the previous value is above the MA to cross and the current value is below it" in new CrossDownSetup {

    ticker.chromosome(bitToEncode.bitIndex) should equal('0')

    encoder.mapMovingAverageBits(ticker, history, encodingMap)

    ticker.chromosome(bitToEncode.bitIndex) should equal('1')
  }

  it should "Set the SMA crossing down bit to 1 when the previous value is above the MA to cross and the current value is equal it" in new CrossDownEqualSetup {

    ticker.chromosome(bitToEncode.bitIndex) should equal('0')

    encoder.mapMovingAverageBits(ticker, history, encodingMap)

    ticker.chromosome(bitToEncode.bitIndex) should equal('1')
  }

  it should "Set the SMA crossing down bit to 0 when the previous value is below the MA to cross" in new AlreadyCrossedDownSetup {

    ticker.chromosome(bitToEncode.bitIndex) should equal('1')

    encoder.mapMovingAverageBits(ticker, history, encodingMap)

    ticker.chromosome(bitToEncode.bitIndex) should equal('0')
  }

  it should "Set the SMA crossing up bit to 0 when there aren't enough days of data to calculate the current value" in
    new InsufficientCurrentMovingAverageDataSetup(CrossingDownDayCalculatedBitEncoding(0, 5, 10)) {

      ticker.chromosome(bitToEncode.bitIndex) should equal('1')

      encoder.mapMovingAverageBits(ticker, history, encodingMap)

      ticker.chromosome(bitToEncode.bitIndex) should equal('0')
    }

  it should "Set the SMA crossing up bit to 0 when there aren't enough days of data to calculate the MA to cross" in
    new InsufficientMovingAverageToCrossDataSetup(CrossingDownDayCalculatedBitEncoding(0, 5, 10)) {

      ticker.chromosome(bitToEncode.bitIndex) should equal('1')

      encoder.mapMovingAverageBits(ticker, history, encodingMap)

      ticker.chromosome(bitToEncode.bitIndex) should equal('0')
    }

  it should "Set the SMA crossing up bit to 0 when there aren't enough days of data to calculate the previous day MA" in
    new InsufficientPreviousDayMovingAverageDataSetup(CrossingDownDayCalculatedBitEncoding(0, 5, 10)) {

      ticker.chromosome(bitToEncode.bitIndex) should equal('1')

      encoder.mapMovingAverageBits(ticker, history, encodingMap)

      ticker.chromosome(bitToEncode.bitIndex) should equal('0')
    }
}


