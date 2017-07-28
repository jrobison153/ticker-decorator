package com.spacecorpshandbook.ticker.spring.stub

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.spring.queue.StringQueue

/**
  * Created by justin on 7/27/17.
  */
class ValidTickerQueueStub extends StringQueue {

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

  val objMapper : ObjectMapper = new ObjectMapper()
    .findAndRegisterModules
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  var lastQueuePoppedFrom: String = _

  /**
    * always returns a valid ticker
    *
    * @param key - represent the underlying queue mechanisms way to identify where to pop a message from
    * @return - the next message if one is available
    */
  override def leftPop(key: String): String = {

    lastQueuePoppedFrom = key

    VALID_TICKER_AS_JSON
  }

  def tickerId = {

    val ticker = objMapper.readValue(VALID_TICKER_AS_JSON, classOf[Ticker])

    ticker.id
  }
}
