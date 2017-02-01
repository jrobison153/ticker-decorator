package com.spacecorpshandbook.ticker.spring.controller

import com.spacecorpshandbook.ticker.core.model.{Ticker, TickerDecoratorResponse}
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RestController}

@RestController
@Configuration
@EnableAutoConfiguration
@RequestMapping(Array("/ticker"))
class TickerDecoratorController {

  @RequestMapping(value = Array("decorate"), method = Array(RequestMethod.POST))
  def decorate(@RequestBody ticker: Ticker): TickerDecoratorResponse = {

    val response : TickerDecoratorResponse = new TickerDecoratorResponse()

    response.setMessage("Symbol decorated: " + ticker.ticker)

    response
  }
}