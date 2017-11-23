package com.spacecorpshandbook.ticker.spring.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.spring.exception.InvalidTickerException
import com.spacecorpshandbook.ticker.spring.wrapper.TickerServiceMirror
import org.springframework.stereotype.Component

/**
  * Redis chromosome decoration task queue.
  */
@Component
class DecorationQueue(queue: StringQueue, service: TickerServiceMirror, objMapper: ObjectMapper) {

  var UNDECORATED_TICKERS = "UNDECORATED_TICKERS"

  def readTicker = {

    val tickerAsJson = queue.leftPop(UNDECORATED_TICKERS)

    if (tickerAsJson != null) {

      val ticker = objMapper.readValue(tickerAsJson, classOf[Ticker])

      if(tickerIsValidForDecoration(ticker)) {

        service.addChromosome(ticker)
      } else {

        throw new InvalidTickerException(tickerAsJson)
      }
    }
  }

  private def tickerIsValidForDecoration(ticker: Ticker): Boolean = {

    var isValidForDecoration = true

    if(
      ticker.id == null
      || ticker.id.isEmpty
      || ticker.ticker == null
      || ticker.ticker.isEmpty
      || ticker.date == null
      || ticker.open == null
      || ticker.close == null
      || ticker.high == null
      || ticker.low == null
      || ticker.volume == null
      || ticker.splitRatio == null
      || ticker.exDividend == null
    ) {

      isValidForDecoration = false
    }

    isValidForDecoration
  }
}
