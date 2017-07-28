package com.spacecorpshandbook.ticker.spring.config

import com.spacecorpshandbook.ticker.core.service.TickerService
import com.spacecorpshandbook.ticker.spring.message.TickerDecorationReceiver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.listener.{ChannelTopic, RedisMessageListenerContainer}

@Configuration
class RedisTaskQueueConfig {

  @Autowired
  var decoratorService: TickerService = _

  @Bean
  def container(connectionFactory: RedisConnectionFactory, listenerAdapter: MessageListenerAdapter): RedisMessageListenerContainer = {

    val container = new RedisMessageListenerContainer
    container.setConnectionFactory(connectionFactory)
    container.addMessageListener(listenerAdapter, new ChannelTopic("DECORATION"))
    container
  }

  @Bean
  def redisTemplate(connectionFactory: RedisConnectionFactory) : StringRedisTemplate = {

    val template = new StringRedisTemplate
    template.setConnectionFactory(connectionFactory)
    connectionFactory.getConnection.setClientName("ticker-decorator".getBytes)
    template
  }

  @Bean
  def listenerAdapter(receiver: TickerDecorationReceiver) : MessageListenerAdapter = {

    new MessageListenerAdapter(receiver, "onMessage")
  }

  @Bean
  def receiver: TickerDecorationReceiver = {

    new TickerDecorationReceiver(decoratorService)
  }
}
