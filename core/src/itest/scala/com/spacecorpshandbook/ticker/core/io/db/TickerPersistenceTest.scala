package com.spacecorpshandbook.ticker.core.io.db

import java.time.LocalDate

import com.spacecorpshandbook.ticker.core.model.Ticker
import org.mongodb.scala.MongoDatabase
import org.scalatest.{AsyncFlatSpec, BeforeAndAfter, BeforeAndAfterAll, Matchers}

import scala.concurrent.Future

class TickerPersistenceTest extends AsyncFlatSpec
  with Matchers
  with BeforeAndAfter
  with BeforeAndAfterAll {

  val ticker: Ticker = new Ticker
  var persistence : TickerPersistence = _
  var initDoneFuture: Future[_] = _

  override def beforeAll() {

    super.beforeAll()

    initDoneFuture = TickerDatabaseSetup.databaseSetup
  }

  override def afterAll() {

    super.afterAll()

    TickerDatabaseSetup.databaseCleanup
  }


  before {

    val mongoDatabase: MongoDatabase = MongoConnection.getDefaultDatabase

    persistence = new TickerPersistence(mongoDatabase)
  }

  behavior of "database search"

  it should "eventually return the number of days of data asked for" in {

    val numDaysOfData = 10

    for {

      complete <- initDoneFuture
      tickers <- persistence.symbol("A").limit(numDaysOfData).beforeDate(LocalDate.now).search()

    } yield {

      assert(tickers.length == numDaysOfData)
    }
  }

  it should "eventually return 365 days of data if limit not specified" in {


    for {

      complete <- initDoneFuture
      tickers <- persistence.symbol("A").beforeDate(LocalDate.now).search()

    } yield {

      assert(tickers.length == 365)
    }
  }

  it should "eventually return number of days of data requested if date not specified" in {

    val numDaysOfData = 10

    for {

      complete <- initDoneFuture
      tickers <- persistence.symbol("A").limit(numDaysOfData).search()

    } yield {

      assert(tickers.length == numDaysOfData)
    }
  }

  it should "eventually return number of days of data requested if ticker symbol not specified" in {

    val numDaysOfData = 10

    for {

      complete <- initDoneFuture
      tickers <- persistence.limit(numDaysOfData).search()

    } yield {
      assert(tickers.length == numDaysOfData)
    }
  }

  it should "eventually return no results when filter date is before any entry in the database" in {

    val numDaysOfData = 10

    for {

      complete <- initDoneFuture
      tickers <- persistence.symbol("A").limit(numDaysOfData).beforeDate(LocalDate.now.minusYears(1000)).search()

    } yield {

      assert(tickers.isEmpty)
    }
  }

  it should "eventually return no results when ticker symbol doesn't exist in the database" in {

    val numDaysOfData = 10

    for {
      complete <- initDoneFuture
      tickers <- persistence.symbol("I don't exist").limit(numDaysOfData).beforeDate(LocalDate.now).search()

    } yield {

      assert(tickers.isEmpty)
    }
  }

  it should "eventually return a ticker when searched for by id" in {

    for {
      complete <- initDoneFuture
      tickers <- persistence.symbol("A").limit(1).search()
      tickersFoundbyId <- persistence.id(tickers.head.id).search()

    } yield {

      assert(tickersFoundbyId.size == 1)
    }
  }

  behavior of "database replace"

  it should "eventually update the ticker in the database" in {

    val newChromosomeValue = "not a real chromosome but easy to validate in update test"

    for {

      complete <- initDoneFuture
      tickers <- persistence.symbol("A").limit(1).search()
      updateResult <- {

        val tickerToUpdate = tickers.head

        tickerToUpdate.chromosome = newChromosomeValue

        persistence.replace(tickerToUpdate)
      }
      updatedTickers <- persistence.id(tickers.head.id).search()

    } yield {

      assert(updatedTickers.head.chromosome.equals(newChromosomeValue))
    }
  }
}
