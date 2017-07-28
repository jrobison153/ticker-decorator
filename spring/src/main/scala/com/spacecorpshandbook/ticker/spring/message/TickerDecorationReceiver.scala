package com.spacecorpshandbook.ticker.spring.message

import com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, ObjectMapper}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.service.TickerService

class TickerDecorationReceiver(decoratorService: TickerService) {

  val objMapper : ObjectMapper = new ObjectMapper()
    .registerModule(new JavaTimeModule)
    .registerModule(DefaultScalaModule)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def onMessage(message: String): Unit = {

    val messageAsJsonNode = objMapper.readValue(message, classOf[JsonNode])

    getEventType(messageAsJsonNode) match {

      case Some(event) => decodeEvent(event, messageAsJsonNode)
      case None => System.out.println(s"""Corrupt message found without eventType field, message was $messageAsJsonNode""")
    }
  }

  private def getEventType(messageAsJsonNode: JsonNode) : Option[String] = {

    val eventTypeNode = messageAsJsonNode get "eventType"

    if(eventTypeNode != null) {

      Some(eventTypeNode.asText())
    } else {

      None
    }
  }

  private def decodeEvent(eventType: String, messageAsJsonNode: JsonNode) = {

    eventType match {

      case "UNDECORATED_TICKER_RECEIVED" => processUndecoratedTickerReceivedEvent(messageAsJsonNode)
      case _ => //ignore events we don't care about
    }
  }


  private def processUndecoratedTickerReceivedEvent(messageAsJsonNode: JsonNode) = {

    getPayload(messageAsJsonNode) match {
      case Some(ticker: Ticker) => decoratorService.addChromosome(ticker)
      case None => System.out.println(s"""Corrupt message found without payload field, message was $messageAsJsonNode""")
    }
  }

  def getPayload(messageAsJsonNode: JsonNode) : Option[Ticker] = {

    if (messageAsJsonNode.has("payload")) {

      val payload = messageAsJsonNode get "payload"

      val ticker = objMapper.readValue(payload.toString, classOf[Ticker])

      Some(ticker)
    } else {

      None
    }
  }
}
