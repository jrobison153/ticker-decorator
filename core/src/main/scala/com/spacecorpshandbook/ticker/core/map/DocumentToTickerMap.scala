package com.spacecorpshandbook.ticker.core.map

import java.time.LocalDate

import com.spacecorpshandbook.ticker.core.model.Ticker
import org.mongodb.scala.bson.BsonValue
import org.mongodb.scala.bson.collection.immutable.Document

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

    document.get("open") match {

      case Some(bsonValue) => createBigDecimalFromBson(bsonValue) match {

          case Some(bigDecimal) => ticker.open = bigDecimal
          case None =>
        }
      case None =>
    }
  }

  private def mapHigh(document: Document, ticker: Ticker) = {
    document.get("high") match {

      case Some(bsonValue) => createBigDecimalFromBson(bsonValue) match {

          case Some(bigDecimal) => ticker.high = bigDecimal
          case None =>
        }
      case None =>
    }
  }

  private def mapLow(document: Document, ticker: Ticker) = {
    document.get("low") match {

      case Some(bsonValue) => createBigDecimalFromBson(bsonValue) match {

        case Some(bigDecimal) => ticker.low = bigDecimal
        case None =>
      }
      case None =>
    }
  }

  private def mapClose(document: Document, ticker: Ticker) = {
    document.get("close") match {

      case Some(bsonValue) => createBigDecimalFromBson(bsonValue) match {

        case Some(bigDecimal) => ticker.close = bigDecimal
        case None =>
      }
      case None =>
    }
  }

  private def mapVolume(document: Document, ticker: Ticker) = {
    document.get("volume") match {

      case Some(bsonValue) => createBigDecimalFromBson(bsonValue) match {

        case Some(bigDecimal) => ticker.volume = bigDecimal
        case None =>
      }
      case None =>
    }
  }

  private def mapExDividend(document: Document, ticker: Ticker) = {
    document.get("exDividend") match {

      case Some(bsonValue) => createBigDecimalFromBson(bsonValue) match {

        case Some(bigDecimal) => ticker.exDividend = bigDecimal
        case None =>
      }
      case None =>
    }
  }

  private def mapSplitRatio(document: Document, ticker: Ticker) = {
    document.get("splitRatio") match {

      case Some(bsonValue) => createBigDecimalFromBson(bsonValue) match {

        case Some(bigDecimal) => ticker.splitRatio = bigDecimal
        case None =>
      }
      case None =>
    }
  }

  private def mapChromosome(document: Document, ticker: Ticker) = {
    val chromosome = document getString "chromosome"

    if (chromosome != null) {

      ticker.chromosome = chromosome
    }
  }

  private def createBigDecimalFromBson(bsonValue: BsonValue) : Option[BigDecimal] = {

    var result: Option[BigDecimal] = None

    if (bsonValue.isDouble) {

      result = Some(BigDecimal(bsonValue.asDouble.getValue))

    } else if (bsonValue.isInt32) {

      result = Some(BigDecimal(bsonValue.asInt32.getValue))

    } else if (bsonValue.isInt64) {

      result = Some(BigDecimal(bsonValue.asInt64.getValue))
    }

    result
  }
}
