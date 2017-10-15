package com.spacecorpshandbook.ticker.spring.message

import org.springframework.data.redis.core.RedisTemplate

/**
  * Message publisher implementation based on Redis supporting general string message publication
  *
  * @param redisTemplate
  */
class RedisStringMessagePublisher(redisTemplate: RedisTemplate[String, String]) extends MessagePublisher {

  override def publish(topic: String, message: String): Unit = {

    redisTemplate.convertAndSend(topic, message)
  }
}
