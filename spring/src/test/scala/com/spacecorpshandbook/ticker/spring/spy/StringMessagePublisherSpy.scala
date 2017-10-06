package com.spacecorpshandbook.ticker.spring.spy

import com.spacecorpshandbook.ticker.spring.message.MessagePublisher

class StringMessagePublisherSpy extends MessagePublisher {

  var lastTopicPublishedTo: String = _
  var lastPublishedEvent: String = _

  override def publish(topic: String, message: String): Unit = {

    lastTopicPublishedTo = topic
    lastPublishedEvent = message
  }
}
