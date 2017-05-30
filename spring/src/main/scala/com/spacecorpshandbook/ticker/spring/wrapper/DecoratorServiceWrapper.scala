package com.spacecorpshandbook.ticker.spring.wrapper

import com.spacecorpshandbook.ticker.core.calculator.{MovingAverageCalculator, SimpleMovingAverageCalculator}
import com.spacecorpshandbook.ticker.core.chromosome.{ChromosomeEncoder, Encoder}
import com.spacecorpshandbook.ticker.core.io.db.{MongoConnection, Persistence, TickerPersistence}
import com.spacecorpshandbook.ticker.core.model.Ticker
import com.spacecorpshandbook.ticker.core.service.{DecoratorService, TickerService}
import org.mongodb.scala.MongoDatabase
import org.springframework.stereotype.Service

import scala.concurrent.Future

/**
  * Wrap the real DecoratorService so it can be managed by Spring
  */

@Service
class DecoratorServiceWrapper extends TickerService {

  val database: MongoDatabase = MongoConnection.getDefaultDatabase
  val persistence: Persistence = new TickerPersistence(database)

  val movingAverageCalculator: MovingAverageCalculator = new SimpleMovingAverageCalculator
  val encoder: Encoder = new ChromosomeEncoder(movingAverageCalculator)

  val wrappedDecorator : DecoratorService = new DecoratorService(persistence, encoder)

  override def addChromosome(ticker: Ticker): Future[Ticker] = {

    wrappedDecorator.addChromosome(ticker)
  }
}
