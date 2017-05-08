package com.spacecorpshandbook.ticker.core.chromosome

object ChromosomeDecoder {

  val DEFAULT_CHROMOSOME: String = "0000000000000000000000000000000000000000000000000000000000000000000"

  val FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(0, 5, 10)
  val FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(1, 5, 20)
  val FIVE_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(2, 5, 50)
  val FIVE_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(3, 5, 100)
  val FIVE_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(4, 5, 200)

  val TEN_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(5, 10, 5)
  val TEN_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(6, 10, 20)
  val TEN_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(7, 10, 100)
  val TEN_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(8, 10, 100)
  val TEN_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(9, 10, 200)

  val TWENTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(10, 20, 5)
  val TWENTY_DAY_SMA_CROSSED_TEN_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(11, 20, 10)
  val TWENTY_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(12, 20, 50)
  val TWENTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(13, 20, 100)
  val TWENTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(14, 20, 200)

  val FIFTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(15, 50, 5)
  val FIFTY_DAY_SMA_CROSSED_TEN_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(16, 50, 10)
  val FIFTY_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(17, 50, 20)
  val FIFTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(18, 50, 100)
  val FIFTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(19, 50, 200)

  val ONE_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(20, 100, 5)
  val ONE_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(21, 100, 10)
  val ONE_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(22, 100, 20)
  val ONE_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(23, 100, 50)
  val ONE_HUNDRED_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(24, 100, 200)

  val TWO_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(25, 200, 5)
  val TWO_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(26, 200, 10)
  val TWO_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(27, 200, 20)
  val TWO_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(28, 200, 50)
  val TWO_HUNDRED_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(29, 200, 100)

  val FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(30, 5, 10)
  val FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(31, 5, 20)
  val FIVE_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(32, 5, 50)
  val FIVE_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(33, 5, 100)
  val FIVE_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(34, 5, 200)

  val TEN_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(35, 10, 5)
  val TEN_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(36, 10, 20)
  val TEN_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(37, 10, 100)
  val TEN_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(38, 10, 100)
  val TEN_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(39, 10, 200)

  val TWENTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(40, 20, 5)
  val TWENTY_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(41, 20, 10)
  val TWENTY_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(42, 20, 50)
  val TWENTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(43, 20, 100)
  val TWENTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(44, 20, 200)

  val FIFTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(45, 50, 5)
  val FIFTY_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(46, 50, 10)
  val FIFTY_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(47, 50, 20)
  val FIFTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(48, 50, 100)
  val FIFTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(49, 50, 200)

  val ONE_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(50, 100, 5)
  val ONE_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(51, 100, 10)
  val ONE_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(52, 100, 20)
  val ONE_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(53, 100, 50)
  val ONE_HUNDRED_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(54, 100, 200)

  val TWO_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(55, 200, 5)
  val TWO_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(56, 200, 10)
  val TWO_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(57, 200, 20)
  val TWO_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(58, 200, 50)
  val TWO_HUNDRED_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN: DayCalculatedBitEncoding = CrossingUpDayCalculatedBitEncoding(59, 200, 100)

  val SMA_CROSSING_UP_ENCODING_MAP: Seq[DayCalculatedBitEncoding] = Seq(
    FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP,
    FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP,
    FIVE_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP,
    FIVE_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP,
    FIVE_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP,
    TEN_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP,
    TEN_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP,
    TEN_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP,
    TEN_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP,
    TEN_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP,
    TWENTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP,
    TWENTY_DAY_SMA_CROSSED_TEN_DAY_SMA_UP,
    TWENTY_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP,
    TWENTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP,
    TWENTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP,
    FIFTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP,
    FIFTY_DAY_SMA_CROSSED_TEN_DAY_SMA_UP,
    FIFTY_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP,
    FIFTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP,
    FIFTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP,
    ONE_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP,
    ONE_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_UP,
    ONE_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP,
    ONE_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP,
    ONE_HUNDRED_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP,
    TWO_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP,
    TWO_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_UP,
    TWO_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP,
    TWO_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP,
    TWO_HUNDRED_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP
  )

  val SMA_CROSSING_DOWN_ENCODING_MAP: Seq[DayCalculatedBitEncoding] = Seq(
    FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN,
    FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN,
    FIVE_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN,
    FIVE_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN,
    FIVE_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN,
    TEN_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN,
    TEN_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN,
    TEN_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN,
    TEN_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN,
    TEN_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN,
    TWENTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN,
    TWENTY_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN,
    TWENTY_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN,
    TWENTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN,
    TWENTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN,
    FIFTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN,
    FIFTY_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN,
    FIFTY_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN,
    FIFTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN,
    FIFTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN,
    ONE_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN,
    ONE_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN,
    ONE_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN,
    ONE_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN,
    ONE_HUNDRED_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN,
    TWO_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN,
    TWO_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN,
    TWO_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN,
    TWO_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN,
    TWO_HUNDRED_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN
  )
}


abstract class DayCalculatedBitEncoding {

  def bitIndex: Int

  def crossingMovingAverage: Int

  def numberOfDaysInCalculation: Int
}

case class CrossingUpDayCalculatedBitEncoding(bitIndex: Int, crossingMovingAverage: Int, numberOfDaysInCalculation: Int)
  extends DayCalculatedBitEncoding

case class CrossingDownDayCalculatedBitEncoding(bitIndex: Int, crossingMovingAverage: Int, numberOfDaysInCalculation: Int)
  extends DayCalculatedBitEncoding