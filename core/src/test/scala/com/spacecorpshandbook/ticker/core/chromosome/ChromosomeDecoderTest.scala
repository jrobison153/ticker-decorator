package com.spacecorpshandbook.ticker.core.chromosome

import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class ChromosomeDecoderTest extends FlatSpec
  with Matchers
  with OneInstancePerTest {

  behavior of "Simple Moving Average crossing up encoding map"

  it should "contain all five day SMA crossing up bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.FIVE_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.FIVE_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.FIVE_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP)
  }

  it should "contain all ten day SMA crossing up bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TEN_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TEN_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TEN_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TEN_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TEN_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP)
  }

  it should "contain all twenty day SMA crossing up bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TWENTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TWENTY_DAY_SMA_CROSSED_TEN_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TWENTY_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TWENTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TWENTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP)
  }

  it should "contain all fifty day SMA crossing up bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.FIFTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.FIFTY_DAY_SMA_CROSSED_TEN_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.FIFTY_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.FIFTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.FIFTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP)
  }

  it should "contain all one hundred day SMA crossing up bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.ONE_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.ONE_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.ONE_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.ONE_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.ONE_HUNDRED_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_UP)
  }

  it should "contain all two hundred day SMA crossing up bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TWO_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TWO_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TWO_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TWO_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_UP)
    ChromosomeDecoder.SMA_CROSSING_UP_ENCODING_MAP should contain (ChromosomeDecoder.TWO_HUNDRED_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_UP)
  }

  it should "contain all five day SMA crossing down bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.FIVE_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.FIVE_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.FIVE_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.FIVE_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.FIVE_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN)
  }

  it should "contain all ten day SMA crossing down bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TEN_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TEN_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TEN_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TEN_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TEN_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN)
  }

  it should "contain all twenty day SMA crossing down bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TWENTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TWENTY_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TWENTY_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TWENTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TWENTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN)
  }

  it should "contain all fifty day SMA crossing down bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.FIFTY_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.FIFTY_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.FIFTY_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.FIFTY_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.FIFTY_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN)
  }

  it should "contain all one hundred day SMA crossing down bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.ONE_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.ONE_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.ONE_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.ONE_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.ONE_HUNDRED_DAY_SMA_CROSSED_TWO_HUNDRED_DAY_SMA_DOWN)
  }

  it should "contain all two hundred day SMA crossing down bit encodings" in {

    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TWO_HUNDRED_DAY_SMA_CROSSED_FIVE_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TWO_HUNDRED_DAY_SMA_CROSSED_TEN_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TWO_HUNDRED_DAY_SMA_CROSSED_TWENTY_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TWO_HUNDRED_DAY_SMA_CROSSED_FIFTY_DAY_SMA_DOWN)
    ChromosomeDecoder.SMA_CROSSING_DOWN_ENCODING_MAP should contain (ChromosomeDecoder.TWO_HUNDRED_DAY_SMA_CROSSED_ONE_HUNDRED_DAY_SMA_DOWN)
  }
}
