package com.spacecorpshandbook.ticker.spring.message

import com.spacecorpshandbook.ticker.spring.spy.DecoratorServiceSpy
import org.scalatest.{FlatSpec, Matchers}

class TickerDecorationReceiverTest extends FlatSpec
  with Matchers {

  val fullJsonMessage = "{\n " +
    " \"id\": \"39d0a2fe-8530-4ca2-b2c3-901570c5d449\",\n" +
    " \"createdTime\": 1500367735804,\n" +
    " \"createdTimePretty\": \"Tue, 18 Jul 2017 08:48:55 GMT\",\n" +
    " \"eventType\": \"UNDECORATED_TICKER_RECEIVED\",\n " +
    " \"payload\": {\n    " +
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
    "  }\n" +
    "}"

  trait Setup {

    var decoratorServiceSpy: DecoratorServiceSpy = _

    def initialize(message: String) = {

      decoratorServiceSpy = new DecoratorServiceSpy()

      val receiver = new TickerDecorationReceiver(decoratorServiceSpy)

      receiver.onMessage(message)
    }
  }

  behavior of "TickerDecorationReceiver when an UNDECORATED_TICKER_RECEIVED event is received"

  it should "add a chromosome to the ticker" in new Setup {

    initialize(fullJsonMessage)

    decoratorServiceSpy.addChromosomeCalled should be(true)
  }

  it should "pass the payload as a Ticker to the decorator service if the payload is present" in new Setup {

    initialize(fullJsonMessage)

    decoratorServiceSpy.tickerPassedForDecoration should not be null
    decoratorServiceSpy.tickerPassedForDecoration.id should equal("596dcb77dccd52d47485ebc9")
  }

  it should "ignore the event if the payload is not present" in new Setup {

    val message: String = "{" +
      " \"eventType\": \"UNDECORATED_TICKER_RECEIVED\"" +
      "}"

    initialize(message)

    decoratorServiceSpy.addChromosomeCalled should be(false)
  }

  behavior of "TickerDecoratorReceiver when an unknown event type message is received"

  it should "ignore the event" in new Setup {

    val message: String = "{" +
      " \"eventType\": \"SOME_OTHER_THING_HAPPENED\"" +
      "}"

    initialize(message)

    decoratorServiceSpy.addChromosomeCalled should be(false)
  }

  behavior of "TickerDecoratorReceiver when an event type not present on message"

  it should "ignore the event" in new Setup {

    val message: String = "{" +
      " \"foo\": \"blah\"" +
      "}"

    initialize(message)

    decoratorServiceSpy.addChromosomeCalled should be(false)
  }
}

