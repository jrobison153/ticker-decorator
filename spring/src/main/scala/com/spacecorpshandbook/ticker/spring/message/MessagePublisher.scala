package com.spacecorpshandbook.ticker.spring.message

/**
  * Interface to abstract message publishing implementation
  */
trait MessagePublisher {

  def publish(topic: String, message: String)
}
