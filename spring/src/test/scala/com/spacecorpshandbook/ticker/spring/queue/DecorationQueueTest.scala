package com.spacecorpshandbook.ticker.spring.queue

import com.spacecorpshandbook.ticker.spring.exception.InvalidTickerException
import com.spacecorpshandbook.ticker.spring.spy.DecoratorServiceMirrorSpy
import com.spacecorpshandbook.ticker.spring.stub.{InvalidTickerQueueStub, ValidTickerQueueStub}
import org.scalatest.{FlatSpec, Matchers}

class DecorationQueueTest extends FlatSpec
  with Matchers {

  behavior of "DecorationQueue when a valid ticker is popped from the queue"

  trait ValidTickerSetup {

    val validTickerQueueStub = new ValidTickerQueueStub
    val decoratorServiceSpy = new DecoratorServiceMirrorSpy
    val decorationQueue = new DecorationQueue(validTickerQueueStub, decoratorServiceSpy)

    decorationQueue.readTicker
  }

  it should "send that ticker to the decoration service" in new ValidTickerSetup {

    decoratorServiceSpy.tickerPassedForDecoration.id should equal(validTickerQueueStub.tickerId)
  }

  it should "read messages from the UNDECORATED_TICKERS queue" in new ValidTickerSetup {

    validTickerQueueStub.lastQueuePoppedFrom should equal("UNDECORATED_TICKERS")
  }

  behavior of "DecorationQueue when an invalid ticker is popped from the queue"

  trait InvalidTickerSetup {

    val invalidTickerQueueStub = new InvalidTickerQueueStub()
    val decoratorServiceSpy = new DecoratorServiceMirrorSpy
  }

  it should "throw an exception when the _id is not set" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateIdNotSet

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the _id is empty" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateIdEmpty

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the symbol is not set" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateTickerSymbolNotSet

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the symbol is empty" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateTickerSymbolEmpty

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the Date is not set" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateDateNotSet

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the Date is empty" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateDateEmpty

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the Open is not set" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateOpenNotSet

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the Open is empty" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateOpenEmpty

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the Close is not set" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateCloseNotSet

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the Close is empty" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateCloseEmpty

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the High is not set" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateHighNotSet

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the High is empty" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateHighEmpty

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the Low is not set" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateLowNotSet

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the Low is empty" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateLowEmpty

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the Volume is not set" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateVolumeNotSet

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the Volume is empty" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateVolumeEmpty

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the SplitRatio is not set" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateSplitRatioNotSet

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the SplitRatio is empty" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateSplitRatioEmpty

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the ExDividend is not set" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateExDividendNotSet

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }

  it should "throw an exception when the ExDividend is empty" in new InvalidTickerSetup {

    invalidTickerQueueStub.invalidateExDividendEmpty

    val decorationQueue = new DecorationQueue(invalidTickerQueueStub, decoratorServiceSpy)

    an [InvalidTickerException] should be thrownBy decorationQueue.readTicker
  }
}
