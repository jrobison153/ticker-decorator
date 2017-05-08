package com.spacecorpshandbook.ticker.core.spy

import java.time.LocalDateTime

import com.mongodb.client.result.UpdateResult
import com.spacecorpshandbook.ticker.core.io.db.Persistence
import com.spacecorpshandbook.ticker.core.model.Ticker
import org.bson.BsonValue

import scala.concurrent.{ExecutionContext, Future}

/**
  * A Persistence spy class that only supports persistence functions, all builder methods simply
  * return 'this'
  */
case class PersistenceSpy() extends Persistence {

  implicit val ec = ExecutionContext.global

  val tickerA = new Ticker()
  val tickerB = new Ticker()

  val dummyTickerSeq = Seq(tickerA, tickerB)

  val searchFuture: Future[Seq[Ticker]] = Future {

    dummyTickerSeq
  }

  var replacedTicker: Ticker = _

  override def search(): Future[Seq[Ticker]] = {

    searchFuture
  }

  override def symbol(tickerSymbol: String): Persistence = this

  override def beforeDate(date: LocalDateTime): Persistence = this

  override def limit(numDays: Int): Persistence = this


  override def replace(ticker: Ticker): Future[UpdateResult] = {

    replacedTicker = ticker

    Future {

      val result = new DummyUpdateResult

      result
    }
  }
}

private class DummyUpdateResult() extends UpdateResult {
  override def getMatchedCount: Long = ???

  override def isModifiedCountAvailable: Boolean = ???

  override def getUpsertedId: BsonValue = ???

  override def wasAcknowledged(): Boolean = ???

  override def getModifiedCount: Long = ???
}
