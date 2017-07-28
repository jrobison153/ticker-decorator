package com.spacecorpshandbook.ticker.spring.config

import com.spacecorpshandbook.ticker.spring.queue.{RedisStringQueue, StringQueue}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class RedisTaskQueueConfig {

  @Bean
  def redisTemplate(connectionFactory: RedisConnectionFactory) : StringRedisTemplate = {

    val template = new StringRedisTemplate
    template.setConnectionFactory(connectionFactory)
    connectionFactory.getConnection.setClientName("ticker-decorator".getBytes)
    template
  }

  @Bean
  def stringQueue(redisTemplate: StringRedisTemplate): StringQueue = {

    new RedisStringQueue(redisTemplate)
  }
}
