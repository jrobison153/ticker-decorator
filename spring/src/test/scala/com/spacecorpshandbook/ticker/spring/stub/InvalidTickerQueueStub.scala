package com.spacecorpshandbook.ticker.spring.stub

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.spacecorpshandbook.ticker.spring.queue.StringQueue

/**
  * Redis template stub that always returns 'ops' that return invalid tickers
  */
class InvalidTickerQueueStub() extends StringQueue {

  val objMapper : ObjectMapper = new ObjectMapper().findAndRegisterModules
  val VALID_TICKER_AS_JSON =  "{\n    " +
    "    \"_id\": \"596dcb77dccd52d47485ebc9\",\n" +
    "    \"ticker\": \"A\",\n" +
    "    \"date\": \"1999-08-17\",\n" +
    "    \"open\": \"45.25\",\n" +
    "    \"high\": \"45.63\",\n" +
    "    \"low\": \"44.81\",\n" +
    "    \"close\": \"45.19\",\n" +
    "    \"volume\": \"1350400.0\",\n" +
    "    \"exDividend\": \"0.0\",\n" +
    "    \"splitRatio\": \"1.0\",\n" +
    "    \"adjOpen\": \"43.003256083661\",\n" +
    "    \"adjHigh\": \"43.364388399943\",\n" +
    "    \"adjLow\": \"42.585102875333\",\n" +
    "    \"adjClose\": \"42.946235191616\",\n" +
    "    \"adjVolume\": \"1350400.0\"\n" +
    "  }\n"

  var tickerAsJson = ""
  /**
    * Always returns an invalid ticker, invalid with regards to the needs of the decoration service
    *
    * @param key - represent the underlying queue mechanisms way to identify where to pop a message from
    * @return - the next message if one is available
    */
  override def leftPop(key: String): String = {

    tickerAsJson
  }

  def invalidateIdNotSet = {

    removeTickerField("_id")
  }

  def invalidateIdEmpty = {

    unsetTickerField("_id")
  }

  def invalidateTickerSymbolNotSet = {

   removeTickerField("ticker")
  }

  def invalidateTickerSymbolEmpty = {

    unsetTickerField("ticker")
  }

  def invalidateDateNotSet = {

   removeTickerField("date")
  }

  def invalidateDateEmpty = {

    unsetTickerField("date")
  }

  def invalidateOpenNotSet = {

   removeTickerField("open")
  }

  def invalidateOpenEmpty = {

    unsetTickerField("open")
  }

  def invalidateCloseNotSet = {

   removeTickerField("close")
  }

  def invalidateCloseEmpty = {

    unsetTickerField("close")
  }

  def invalidateHighNotSet = {

   removeTickerField("high")
  }

  def invalidateHighEmpty = {

    unsetTickerField("high")
  }

  def invalidateLowNotSet = {

   removeTickerField("low")
  }

  def invalidateLowEmpty = {

    unsetTickerField("low")
  }

  def invalidateVolumeNotSet = {

   removeTickerField("volume")
  }

  def invalidateVolumeEmpty = {

    unsetTickerField("volume")
  }

  def invalidateSplitRatioNotSet = {

   removeTickerField("splitRatio")
  }

  def invalidateSplitRatioEmpty = {

    unsetTickerField("splitRatio")
  }

  def invalidateExDividendNotSet = {

   removeTickerField("exDividend")
  }

  def invalidateExDividendEmpty = {

    unsetTickerField("exDividend")
  }

  private def removeTickerField(field: String) = {

    val tickerJsonNode = objMapper.readTree(VALID_TICKER_AS_JSON)

    tickerJsonNode.asInstanceOf[ObjectNode].remove(field)

    tickerAsJson = tickerJsonNode.toString
  }

  private def unsetTickerField(field: String) = {

    val tickerJsonNode = objMapper.readTree(VALID_TICKER_AS_JSON)

    tickerJsonNode.asInstanceOf[ObjectNode].put(field, "")

    tickerAsJson = tickerJsonNode.toString
  }

}
