package com.spacecorpshandbook.ticker.spring.controller

import java.util.concurrent.TimeUnit

import com.fasterxml.jackson.databind.ObjectMapper
import com.spacecorpshandbook.ticker.core.model.{Ticker, TickerDecoratorResponse}
import com.spacecorpshandbook.ticker.spring.message.MessagePublisher
import com.spacecorpshandbook.ticker.spring.wrapper.TickerServiceMirror
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RestController}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

@RestController
@Configuration
@EnableAutoConfiguration
@RequestMapping(Array("/ticker"))
class TickerDecoratorController(decoratorService: TickerServiceMirror, messagePublisher: MessagePublisher) {

  val objMapper : ObjectMapper = new ObjectMapper().findAndRegisterModules

  @RequestMapping(value = Array("decorate"), method = Array(RequestMethod.POST))
  def decorate(@RequestBody tickerToDecorate: Ticker): TickerDecoratorResponse = {

    val decorationDone: Future[Ticker] = decoratorService.addChromosome(tickerToDecorate)

    /*
    TODO: is there a better way than blocking the future? This would be easy in Node.
     */
    val updatedTicker = Await.result(decorationDone, Duration(30, TimeUnit.SECONDS))

    val response: TickerDecoratorResponse = new TickerDecoratorResponse()

    response.setMessage("Symbol decorated")
    response.ticker = updatedTicker

    response
  }
}