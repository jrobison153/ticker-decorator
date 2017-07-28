package com.spacecorpshandbook.ticker.spring.queue

/**
  * interface for queue based operations that works only on queues
  */
trait StringQueue {

  /**
    * Read a message from the left of a queue
    *
    * @param key - represent the underlying queue mechanisms way to identify where to pop a message from
    * @return - the next message if one is available
    */
  def leftPop(key: String) : String

}
