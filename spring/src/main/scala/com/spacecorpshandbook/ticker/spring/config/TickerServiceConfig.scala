package com.spacecorpshandbook.ticker.spring.config

import com.spacecorpshandbook.ticker.core.calculator.{MovingAverageCalculator, SimpleMovingAverageCalculator}
import com.spacecorpshandbook.ticker.core.chromosome.{ChromosomeEncoder, Encoder}
import com.spacecorpshandbook.ticker.core.io.db.{MongoConnection, Persistence, TickerPersistence}
import com.spacecorpshandbook.ticker.core.service.{DecoratorService, TickerService}
import org.mongodb.scala.MongoDatabase
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class TickerServiceConfig {

  @Bean
  def decoratorService(persistence: Persistence, encoder: Encoder): TickerService = {

    new DecoratorService(persistence, encoder)
  }

  @Bean
  def persistence(): Persistence = {

    val database: MongoDatabase = MongoConnection.getDefaultDatabase
    val persistence: Persistence = new TickerPersistence(database)

    persistence
  }

  @Bean
  def encoder(): Encoder = {

    val movingAverageCalculator: MovingAverageCalculator = new SimpleMovingAverageCalculator
    val encoder: Encoder = new ChromosomeEncoder(movingAverageCalculator)

    encoder
  }
}
