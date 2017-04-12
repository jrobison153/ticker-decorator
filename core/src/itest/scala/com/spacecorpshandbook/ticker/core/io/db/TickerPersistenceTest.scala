package com.spacecorpshandbook.ticker.core.io.db

import java.time.LocalDateTime

import com.spacecorpshandbook.ticker.core.constant.Database._
import com.spacecorpshandbook.ticker.core.model.Ticker
import de.flapdoodle.embed.mongo.config.{IMongodConfig, MongodConfigBuilder, Net}
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.{MongodExecutable, MongodStarter}
import de.flapdoodle.embed.process.runtime.Network
import org.mongodb.scala.bson.collection.immutable.Document
import org.scalatest.{AsyncFlatSpec, BeforeAndAfter, Matchers, OneInstancePerTest}

import scala.io.Source

class TickerPersistenceTest extends AsyncFlatSpec
  with Matchers
  with OneInstancePerTest
  with BeforeAndAfter {

  var mongodExecutable: MongodExecutable = _
  val ticker: Ticker = new Ticker
  var persistence: TickerPersistence = _

  before {
    try {
      val starter: MongodStarter = MongodStarter.getDefaultInstance
      val bindIp: String = "localhost"
      val port: Int = 12345

      val mongodConfig: IMongodConfig = new MongodConfigBuilder()
        .version(Version.Main.PRODUCTION)
        .net(new Net(bindIp, port, Network.localhostIsIPv6()))
        .build

      mongodExecutable = starter.prepare(mongodConfig)
      mongodExecutable.start()

      val mongoDb = MongoConnection.getDefaultDatabase(12345)
      persistence = TickerPersistence(mongoDb)
      val collection = mongoDb.getCollection(STOCK_TICKER_COLLECTION)

      val tickerDataStream = getClass.getClassLoader.getResourceAsStream("tickerData.json")
      Source.fromInputStream(tickerDataStream)

      for (json <- Source.fromInputStream(tickerDataStream).getLines()) {

        collection.insertOne(Document(json)).head()
      }
    }
    catch {

      case ex: Exception =>
        if (mongodExecutable != null) {

          mongodExecutable.stop()
        }
    }
  }

  after {

    if (mongodExecutable != null) {

      mongodExecutable.stop()
    }
  }

  behavior of "database search"

  it should "eventually return the number of days of data asked for" in {

    val numDaysOfData = 10

    val searchDoneFuture = persistence
      .symbol("A")
      .limit(numDaysOfData)
      .beforeDate(LocalDateTime.now)
      .search()

    searchDoneFuture map { tickers =>

      assert(tickers.length == numDaysOfData)
    }
  }

  it should "eventually return 365 days of data if limit not specified" in {

    val searchDoneFuture = persistence
      .symbol("A")
      .beforeDate(LocalDateTime.now)
      .search()

    searchDoneFuture map { tickers =>

      assert(tickers.length == 365)
    }
  }

  it should "eventually return number of days of data requested if date not specified" in {

    val numDaysOfData = 10

    val searchDoneFuture = persistence
      .symbol("A")
      .limit(numDaysOfData)
      .search()

    searchDoneFuture map { tickers =>

      assert(tickers.length == numDaysOfData)
    }
  }

  it should "eventually return number of days of data requested if ticker symbol not specified" in {

    val numDaysOfData = 10

    val searchDoneFuture = persistence
      .limit(numDaysOfData)
      .search()

    searchDoneFuture map { tickers =>

      assert(tickers.length == numDaysOfData)
    }
  }

  it should "eventually return no results when filter date is before any entry in the database" in {

    val numDaysOfData = 10

    val searchDoneFuture = persistence
      .symbol("A")
      .limit(numDaysOfData)
      .beforeDate(LocalDateTime.now.minusYears(1000))
      .search()

    searchDoneFuture map { tickers =>

      assert(tickers.isEmpty)
    }
  }


  it should "eventually return no results when ticker symbol doesn't exist in the database" in {

    val numDaysOfData = 10

    val searchDoneFuture = persistence
      .symbol("I don't exist")
      .limit(numDaysOfData)
      .beforeDate(LocalDateTime.now)
      .search()

    searchDoneFuture map { tickers =>

      assert(tickers.isEmpty)
    }
  }

  it should "eventually return a ticker when searched for by id" in {

    for {

      tickers <- persistence.symbol("A").limit(1).search()
      tickersFoundbyId <- persistence.id(tickers.head.id).search()

    } yield {

      assert(tickersFoundbyId.size == 1)
    }
  }

  behavior of "database update"

  it should "eventually return the ticker that was updated in the database" in {

    val newChromosomeValue = "not a real chromosome but easy to validate in update test"

    for {

      tickers <- persistence.symbol("A").limit(1).search()
      updateResult <- {

        val tickerToUpdate = tickers.head

        tickerToUpdate.chromosome = newChromosomeValue

        persistence.update(tickerToUpdate)
      }
      updatedTickers <- persistence.id(tickers.head.id).search()

    } yield {

      assert(updatedTickers.head.chromosome.equals(newChromosomeValue))
    }
  }
}
