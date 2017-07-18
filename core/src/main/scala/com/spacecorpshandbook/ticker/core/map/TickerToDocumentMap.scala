package com.spacecorpshandbook.ticker.core.map

import com.spacecorpshandbook.ticker.core.model.Ticker
import org.bson.types.ObjectId
import org.mongodb.scala.Document
import org.mongodb.scala.bson.BsonDouble

object TickerToDocumentMap {

  def map(ticker: Ticker) : Document = {

    var document = Document()

    if(ticker.id != null && !ticker.id.isEmpty) {

      document = document + ("_id" -> new ObjectId(ticker.id))
    }

    if(ticker.ticker != null) {

      document = document + ("ticker" -> ticker.ticker)
    }

    if(ticker.date != null) {

      document = document + ("date" -> ticker.date.toString)
    }

    if(ticker.open != null) {

      document = document + ("open" -> new BsonDouble(ticker.open.toDouble))
    }

    if(ticker.close != null) {

      document = document + ("close" -> new BsonDouble(ticker.close.toDouble))
    }

    if(ticker.high != null) {

      document = document + ("high" -> new BsonDouble(ticker.high.toDouble))
    }

    if(ticker.low != null) {

      document = document + ("low" -> new BsonDouble(ticker.low.toDouble))
    }

    if(ticker.volume != null) {

      document = document + ("volume" -> new BsonDouble(ticker.volume.toDouble))
    }

    if(ticker.exDividend != null) {

      document = document + ("exDividend" -> new BsonDouble(ticker.exDividend.toDouble))
    }

    if(ticker.splitRatio != null) {

      document = document + ("splitRatio" -> new BsonDouble(ticker.splitRatio.toDouble))
    }

    if(ticker.chromosome != null) {

      document = document + ("chromosome" -> ticker.chromosome)
    }

    document
  }
}
