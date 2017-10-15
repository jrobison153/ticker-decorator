package com.spacecorpshandbook.ticker.spring.wrapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.spring.spy.{DecoratorServiceSpy, StringMessagePublisherSpy}
import com.spacecorpshandbook.ticker.spring.stub.ExceptionThrowingDecoratorServiceStub
import org.scalatest.{AsyncFlatSpec, Matchers}

import scala.concurrent.ExecutionContext

class DecoratorServiceWrapperTest extends AsyncFlatSpec
  with Matchers {

  implicit val ec = ExecutionContext.global

  val objMapper: ObjectMapper = new ObjectMapper().findAndRegisterModules

  behavior of "a DecorationServiceWrapper when adding a chromosome"

  it should "delegate to the wrapped service" in {

    val wrappedServiceSpy = new DecoratorServiceSpy
    val messagePublisherSpy = new StringMessagePublisherSpy
    val serviceWrapper = new DecoratorServiceWrapper(wrappedServiceSpy, messagePublisherSpy)

    serviceWrapper.addChromosome(new Ticker) map { _ =>

      wrappedServiceSpy.addChromosomeCalled should be(true)
    }
  }

  it should "publish a TICKER_DECORATED event to TICKER_BATCH_PROCESSING topic on success" in {

    val wrappedServiceSpy = new DecoratorServiceSpy
    val messagePublisherSpy = new StringMessagePublisherSpy
    val serviceWrapper = new DecoratorServiceWrapper(wrappedServiceSpy, messagePublisherSpy)

    serviceWrapper.addChromosome(new Ticker) map { _ =>

      val message = messagePublisherSpy.lastPublishedEvent
      val messageAsJsonNode = objMapper.readTree(message)
      val eventName = messageAsJsonNode.get("name").asText()

      messagePublisherSpy.lastTopicPublishedTo should equal("TICKER_BATCH_PROCESSING")
      eventName should equal("TICKER_DECORATED")
    }
  }

  it should "not publish a TICKER_DECORATED event on decoration failure" in {

    val wrappedServiceSpy = new ExceptionThrowingDecoratorServiceStub
    val messagePublisherSpy = new StringMessagePublisherSpy
    val serviceWrapper = new DecoratorServiceWrapper(wrappedServiceSpy, messagePublisherSpy)

    val ticker = new Ticker
    serviceWrapper.addChromosome(ticker) map { _ =>

      val message = messagePublisherSpy.lastPublishedEvent

      message should equal(null)
    }
  }

  it should "not return a ticker w/o a chromosome on decoration failure" in {

    val wrappedServiceSpy = new ExceptionThrowingDecoratorServiceStub
    val messagePublisherSpy = new StringMessagePublisherSpy
    val serviceWrapper = new DecoratorServiceWrapper(wrappedServiceSpy, messagePublisherSpy)

    val ticker = new Ticker
    serviceWrapper.addChromosome(ticker) map { decoratedTicker =>

      decoratedTicker.chromosome should have length 0
    }
  }
}
