package com.spacecorpshandbook.ticker.core.io.db

import java.time.LocalDateTime

import com.mongodb.client.result.UpdateResult
import com.spacecorpshandbook.ticker.core.model.Ticker

import scala.concurrent.Future

/**
  * Created by justin on 4/3/17.
  */
trait Persistence {

  def search(): Future[Seq[Ticker]]
  def symbol(tickerSymbol: String): Persistence
  def beforeDate(date: LocalDateTime): Persistence
  def limit(numDays: Int): Persistence
  def id(id: String): Persistence
  def replace(ticker: Ticker): Future[UpdateResult]
}
