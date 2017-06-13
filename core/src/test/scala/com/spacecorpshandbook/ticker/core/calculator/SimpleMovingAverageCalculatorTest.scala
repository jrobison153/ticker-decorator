package com.spacecorpshandbook.ticker.core.calculator

import com.spacecorpshandbook.ticker.core.model.Ticker
import org.scalatest.{FlatSpec, Matchers}

class SimpleMovingAverageCalculatorTest extends FlatSpec
  with Matchers {

  trait SmaSetup {

    def createTickerHistory(data: Seq[BigDecimal]): Seq[Ticker] = {

      data map { x =>

        val ticker = new Ticker
        ticker.close = x

        ticker
      }
    }

    val smaCalculator = new SimpleMovingAverageCalculator
  }

  behavior of "a simple moving average calculator when there are enough days of data for the calculation"

  it should "calculate the simple moving average for all of the data to two decimal places" in new SmaSetup {

    val data = Seq(BigDecimal(34.51), BigDecimal(35.11), BigDecimal(45.60))

    val tickers = createTickerHistory(data)

    val simpleMovingAverage = smaCalculator.calculateForDays(tickers, 3)

    simpleMovingAverage.get should equal(BigDecimal(38.40))
  }

  it should "calculate the simple moving average for a subset of the data" in new SmaSetup {

    val data = Seq(BigDecimal(34.51), BigDecimal(35.11), BigDecimal(45.60))

    val tickers = createTickerHistory(data)

    val simpleMovingAverage = smaCalculator.calculateForDays(tickers, 2)

    simpleMovingAverage.get should equal(BigDecimal(34.81))
  }

  it should "round the result down to the nearest hundredth" in new SmaSetup {

    val data = Seq(BigDecimal(264.51), BigDecimal(279.17), BigDecimal(285.90))

    val tickers = createTickerHistory(data)

    val simpleMovingAverage = smaCalculator.calculateForDays(tickers, 3)

    simpleMovingAverage.get should equal(BigDecimal(276.52))
  }

  it should "calculate the simple moving average for the previous day to two decimal places" in new SmaSetup {

    val data = Seq(BigDecimal(34.51), BigDecimal(35.11), BigDecimal(45.60), BigDecimal(55.87), BigDecimal(58.76))

    val tickers = createTickerHistory(data)

    val simpleMovingAverage = smaCalculator.calculatePreviousDayForDays(tickers, 4)

    simpleMovingAverage.get should equal(BigDecimal(48.83))
  }

  it should "calculate the previous day simple moving average for a subset of the data" in new SmaSetup {

    val data = Seq(BigDecimal(34.51), BigDecimal(35.11), BigDecimal(45.60), BigDecimal(55.87), BigDecimal(58.76))

    val tickers = createTickerHistory(data)

    val simpleMovingAverage = smaCalculator.calculatePreviousDayForDays(tickers, 3)

    simpleMovingAverage.get should equal(BigDecimal(45.52))

  }

  behavior of "a simple moving average calculator when there are not enough days of data for the calculation"

  it should "return an optional None" in new SmaSetup {

    val data = Seq(BigDecimal(34.51), BigDecimal(35.11), BigDecimal(45.60))

    val tickers = createTickerHistory(data)

    val simpleMovingAverage = smaCalculator.calculatePreviousDayForDays(tickers, 5)

    simpleMovingAverage should equal(None)
  }

  behavior of "a simple moving average calculator when there is no history"

  it should "return an optional None" in new SmaSetup {

    val simpleMovingAverage = smaCalculator.calculatePreviousDayForDays(Seq(), 5)

    simpleMovingAverage should equal(None)
  }

}
