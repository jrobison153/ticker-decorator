package com.spacecorpshandbook.ticker.spring.config

import com.spacecorpshandbook.ticker.spring.queue.DecorationQueue
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.{EnableAsync, Scheduled}

@Configuration
@EnableAsync
class QueueConfig(queue: DecorationQueue) {

  @Scheduled(fixedRate = 100)
  def pollQueue() = {

    queue.readTicker
  }
}
