package com.spacecorpshandbook.ticker.spring.config

import com.spacecorpshandbook.ticker.spring.message.{MessagePublisher, RedisStringMessagePublisher}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.redis.core.StringRedisTemplate

/**
  * Configure Redis pub/sub
  */
@Configuration
class RedisPubSubConfig {

  @Bean
  def batchProcessingPublisher(redisTemplate: StringRedisTemplate) : MessagePublisher = {

    new RedisStringMessagePublisher(redisTemplate)
  }
}
