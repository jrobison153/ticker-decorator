package com.spacecorpshandbook.ticker.spring.queue

import org.springframework.data.redis.core.RedisTemplate

/**
  * StringQueue wrapper around redis
  */
class RedisStringQueue(redisTemplate: RedisTemplate[String, String]) extends StringQueue {

  val listOps = redisTemplate.opsForList

  /**
    * Read a message from the left of a queue
    *
    * @param key - represent the underlying queue mechanisms way to identify where to pop a message from
    * @return - the next message if one is available
    */
  override def leftPop(key: String): String = {

    listOps.leftPop(key)
  }
}
