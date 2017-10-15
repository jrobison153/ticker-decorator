package com.spacecorpshandbook.ticker.spring.wrapper

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.service.TickerService
import com.spacecorpshandbook.ticker.spring.message.MessagePublisher
import org.springframework.stereotype.Service

import scala.concurrent.{ExecutionContext, Future}

/**
  * Wrap the real DecoratorService so it can be managed by Spring
  */

@Service
class DecoratorServiceWrapper(wrappedDecorator: TickerService, messagePublisher: MessagePublisher) extends TickerServiceMirror {

  implicit val ec = ExecutionContext.global

    def addChromosome(ticker: Ticker): Future[Ticker] = {

    val doneFuture = for {

      decoratedTicker <- wrappedDecorator.addChromosome(ticker) recover {
        case e: Exception =>
          ticker.chromosome = ""
          ticker
      }

      _ <- {

        if (decoratedTicker.chromosome != null && !decoratedTicker.chromosome.isEmpty) {

          val nodeFactory = JsonNodeFactory.instance
          val tickerDecoratedEvent = nodeFactory.objectNode
          tickerDecoratedEvent.put("name", "TICKER_DECORATED")

          messagePublisher.publish("TICKER_BATCH_PROCESSING", tickerDecoratedEvent.toString)
        }

        Future {}
      }
    } yield {

      decoratedTicker
    }

    doneFuture
  }
}
