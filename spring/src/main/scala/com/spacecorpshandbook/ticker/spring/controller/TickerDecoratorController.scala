package com.spacecorpshandbook.ticker.spring.controller

import java.util.concurrent.TimeUnit

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.spacecorpshandbook.ticker.core.model.{Ticker, TickerDecoratorResponse}
import com.spacecorpshandbook.ticker.core.service.TickerService
import com.spacecorpshandbook.ticker.spring.message.MessagePublisher
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RestController}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

@RestController
@Configuration
@EnableAutoConfiguration
@RequestMapping(Array("/ticker"))
class TickerDecoratorController(decoratorService: TickerService, messagePublisher: MessagePublisher) {

  val objMapper : ObjectMapper = new ObjectMapper().findAndRegisterModules

  @RequestMapping(value = Array("decorate"), method = Array(RequestMethod.POST))
  def decorate(@RequestBody tickerToDecorate: Ticker): TickerDecoratorResponse = {

    val decorationDone: Future[Ticker] = decoratorService.addChromosome(tickerToDecorate)

    /*
    TODO: is there a better way than blocking the future? This would be easy in Node.
     */
    val updatedTicker = Await.result(decorationDone, Duration(30, TimeUnit.SECONDS))

    publishTickerDecoratedEvent

    val response: TickerDecoratorResponse = new TickerDecoratorResponse()

    response.setMessage("Symbol decorated")
    response.ticker = updatedTicker

    response
  }

  private def publishTickerDecoratedEvent = {

    val nodeFactory = JsonNodeFactory.instance
    val messageNode = nodeFactory.objectNode
    messageNode.put("name", "TICKER_DECORATED")

    messagePublisher.publish("TICKER_BATCH_PROCESSING", messageNode.toString)
  }
}