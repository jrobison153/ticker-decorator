package com.spacecorpshandbook.ticker.core.map

import java.time.LocalDate

import com.spacecorpshandbook.ticker.core.model.Ticker
import org.bson.Document

/**
  * Maps a BSON document to a Ticker object
  */
object DocumentToTickerMap {

  def map(document: Document): Ticker = {

    val ticker = new Ticker

    mapId(document, ticker)

    mapSymbol(document, ticker)

    mapDate(document, ticker)

    mapOpen(document, ticker)

    mapHigh(document, ticker)

    mapLow(document, ticker)

    mapClose(document, ticker)

    mapVolume(document, ticker)

    mapExDividend(document, ticker)

    mapSplitRatio(document, ticker)

    mapChromosome(document, ticker)

    ticker
  }

  private def mapId(document: Document, ticker: Ticker) = {
    val id = document getObjectId "_id"

    if (id != null) {

      ticker.id = id.toHexString
    }
  }

  private def mapSymbol(document: Document, ticker: Ticker) = {
    val symbol = document getString "ticker"

    if (symbol != null) {

      ticker.ticker = symbol
    }
  }

  private def mapDate(document: Document, ticker: Ticker) = {
    val date = document getString "date"

    if (date != null && !date.isEmpty) {

      ticker.date = LocalDate.parse(date)
    }
  }

  private def mapOpen(document: Document, ticker: Ticker) = {
    val open = document getDouble "open"

    if (open != null) {

      ticker.open = BigDecimal(open)
    }
  }

  private def mapHigh(document: Document, ticker: Ticker) = {
    val high = document getDouble "high"

    if (high != null) {

      ticker.high = BigDecimal(high)
    }
  }

  private def mapLow(document: Document, ticker: Ticker) = {
    val low = document getDouble "low"

    if (low != null) {

      ticker.low = BigDecimal(low)
    }
  }

  private def mapClose(document: Document, ticker: Ticker) = {
    val close = document getDouble "close"

    if (close != null) {

      ticker.close = BigDecimal(close)
    }
  }

  private def mapVolume(document: Document, ticker: Ticker) = {
    val volume = document getDouble "volume"

    if (volume != null) {

      ticker.volume = BigDecimal(volume)
    }
  }

  private def mapExDividend(document: Document, ticker: Ticker) = {
    val exDividend = document getDouble "exDividend"

    if (exDividend != null) {

      ticker.exDividend = BigDecimal(exDividend)
    }
  }

  private def mapSplitRatio(document: Document, ticker: Ticker) = {
    val splitRatio = document getDouble "splitRatio"

    if (splitRatio != null) {

      ticker.splitRatio = BigDecimal(splitRatio)
    }
  }

  private def mapChromosome(document: Document, ticker: Ticker) = {
    val chromosome = document getString "chromosome"

    if (chromosome != null) {

      ticker.chromosome = chromosome
    }
  }
}
