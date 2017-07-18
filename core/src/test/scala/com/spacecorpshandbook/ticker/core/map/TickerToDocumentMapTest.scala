package com.spacecorpshandbook.ticker.core.map

import java.time.LocalDate

import com.spacecorpshandbook.ticker.core.model.Ticker
import org.bson.types.ObjectId
import org.scalatest.{FlatSpec, Matchers}

class TickerToDocumentMapTest extends FlatSpec
  with Matchers {

  behavior of "A Ticker object to BSON Document Mapper"

  it should "map the id when present" in {

    val ticker = new Ticker
    val objectId = new ObjectId()

    ticker.id = objectId.toHexString

    val document = TickerToDocumentMap map ticker

    document.getObjectId("_id").toHexString should equal(ticker.id)
  }

  it should "not map the id when it isn't present" in {

    val ticker = new Ticker
    ticker.id = null

    val document = TickerToDocumentMap map ticker

    document.getObjectId("_id") should be(null)
  }

  it should "not map the id when it is empty" in {

    val ticker = new Ticker
    ticker.id = ""

    val document = TickerToDocumentMap map ticker

    document.getObjectId("_id") should be(null)
  }

  it should "map the symbol when present" in {

    val ticker = new Ticker
    ticker.ticker = "AAA"

    val document = TickerToDocumentMap map ticker

    document.getString("ticker") should equal(ticker.ticker)
  }

  it should "not map the symbol when it isn't present" in {

    val ticker = new Ticker
    ticker.ticker = null

    val document = TickerToDocumentMap map ticker

    document.getString("ticker") should be(null)
  }

  it should "map the date when present" in {

    val ticker = new Ticker
    ticker.date = LocalDate.now

    val document = TickerToDocumentMap map ticker

    document.getString("date") should equal(ticker.date.toString)
  }

  it should "not map the date when it isn't present" in {

    val ticker = new Ticker
    ticker.date = null

    val document = TickerToDocumentMap map ticker

    document.getDouble("date") should be(null)
  }

  it should "map the open when present" in {

    val ticker = new Ticker
    ticker.open = BigDecimal(23.22)

    val document = TickerToDocumentMap map ticker

    document.getDouble("open") should equal(ticker.open)
  }

  it should "not map the open when it isn't present" in {

    val ticker = new Ticker
    ticker.open = null

    val document = TickerToDocumentMap map ticker

    document.getDouble("open") should be(null)
  }

  it should "map the close when present" in {

    val ticker = new Ticker
    ticker.close = BigDecimal(23.22)

    val document = TickerToDocumentMap map ticker

    document.getDouble("close") should equal(ticker.close)
  }

  it should "not map the close when it isn't present" in {

    val ticker = new Ticker
    ticker.close = null

    val document = TickerToDocumentMap map ticker

    document.getDouble("close") should be(null)
  }

  it should "map the high when present" in {

    val ticker = new Ticker
    ticker.high = BigDecimal(23.22)

    val document = TickerToDocumentMap map ticker

    document.getDouble("high") should equal(ticker.high)
  }

  it should "not map the high when it isn't present" in {

    val ticker = new Ticker
    ticker.high = null

    val document = TickerToDocumentMap map ticker

    document.getDouble("high") should be(null)
  }

  it should "map the low when present" in {

    val ticker = new Ticker
    ticker.low = BigDecimal(23.22)

    val document = TickerToDocumentMap map ticker

    document.getDouble("low") should equal(ticker.low)
  }

  it should "not map the low when it isn't present" in {

    val ticker = new Ticker
    ticker.low = null

    val document = TickerToDocumentMap map ticker

    document.getDouble("low") should be(null)
  }

  it should "map the volume when present" in {

    val ticker = new Ticker
    ticker.volume = BigDecimal(23.22)

    val document = TickerToDocumentMap map ticker

    document.getDouble("volume") should equal(ticker.volume)
  }

  it should "not map the volume when it isn't present" in {

    val ticker = new Ticker
    ticker.volume = null

    val document = TickerToDocumentMap map ticker

    document.getDouble("volume") should be(null)
  }

  it should "map the exDividend when present" in {

    val ticker = new Ticker
    ticker.exDividend = BigDecimal(23.22)

    val document = TickerToDocumentMap map ticker

    document.getDouble("exDividend") should equal(ticker.exDividend)
  }

  it should "not map the exDividend when it isn't present" in {

    val ticker = new Ticker
    ticker.exDividend = null

    val document = TickerToDocumentMap map ticker

    document.getDouble("exDividend") should be(null)
  }

  it should "map the splitRatio when present" in {

    val ticker = new Ticker
    ticker.splitRatio = BigDecimal(23.22)

    val document = TickerToDocumentMap map ticker

    document.getDouble("splitRatio") should equal(ticker.splitRatio)
  }

  it should "not map the splitRatio when it isn't present" in {

    val ticker = new Ticker
    ticker.splitRatio = null

    val document = TickerToDocumentMap map ticker

    document.getDouble("splitRatio") should be(null)
  }

  it should "map the chromosome when present" in {

    val ticker = new Ticker
    ticker.chromosome = "0101010101010101000001010100111"

    val document = TickerToDocumentMap map ticker

    document.getString("chromosome") should equal(ticker.chromosome)
  }

  it should "not map the chromosome when it isn't present" in {

    val ticker = new Ticker
    ticker.chromosome = null

    val document = TickerToDocumentMap map ticker

    document.getString("chromosome") should be(null)
  }
}
