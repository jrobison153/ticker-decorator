package com.spacecorpshandbook.ticker.core.map

import java.time.LocalDate

import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeDecoder
import org.bson.BsonObjectId
import org.bson.types.ObjectId
import org.mongodb.scala.bson.collection.immutable.Document
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by justin on 7/14/17.
  */
class DocumentToTickerMapTest extends FlatSpec
  with Matchers {

  behavior of "A BSON Document to Ticker POJO Mapper"


  it should "map the _id when present" in {

    val expectedId = new ObjectId
    val id = new BsonObjectId(expectedId)
    val document =  Document("_id" -> id)

    val ticker = DocumentToTickerMap.map(document)

    ticker.id should equal(expectedId.toHexString)
  }

  it should "not map the _id when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.id should be ("")
  }

  it should "map the symbol when present" in {

    val symbol = "FOO"
    val document =  Document("ticker" -> symbol)

    val ticker = DocumentToTickerMap.map(document)

    ticker.ticker should equal(symbol)
  }

  it should "not map the symbol when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.ticker should be ("")
  }

  it should "map the date when present" in {

    val date = LocalDate.now
    val document =  Document("date" -> date.toString)

    val ticker = DocumentToTickerMap.map(document)

    ticker.date should equal(date)
  }

  it should "not map the date when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.date should be (null)
  }

  it should "not map the date when it is empty" in {

    val document =  Document("date" -> "")

    val ticker = DocumentToTickerMap.map(document)

    ticker.date should be (null)
  }

  it should "map the open value when present" in {

    val document =  Document("open" -> 23.45)

    val ticker = DocumentToTickerMap.map(document)

    ticker.open should equal(23.45)
  }

  it should "not map the open value when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.open should be (null)
  }

  it should "map the high value when present" in {

    val document =  Document("high" -> 23.46)

    val ticker = DocumentToTickerMap.map(document)

    ticker.high should equal(23.46)
  }

  it should "not map the high value when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.high should be (null)
  }

  it should "map the low value when present" in {

    val document =  Document("low" -> 23.36)

    val ticker = DocumentToTickerMap.map(document)

    ticker.low should equal(23.36)
  }

  it should "not map the low value when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.low should be (null)
  }

  it should "map the close value when present" in {

    val document =  Document("close" -> 25.36)

    val ticker = DocumentToTickerMap.map(document)

    ticker.close should equal(25.36)
  }

  it should "not map the close value when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.close should be (null)
  }

  it should "map the volume value when present" in {

    val document =  Document("volume" -> 4.47399e+07)

    val ticker = DocumentToTickerMap.map(document)

    ticker.volume should equal(44739900)
  }

  it should "not map the volume value when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.volume should be (null)
  }

  it should "map the exDividend value when present" in {

    val document =  Document("exDividend" -> 0.124)

    val ticker = DocumentToTickerMap.map(document)

    ticker.exDividend should equal(0.124)
  }

  it should "not map the exDividend value when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.exDividend should be (null)
  }

  it should "map the splitRatio value when present" in {

    val document =  Document("splitRatio" -> 1.5)

    val ticker = DocumentToTickerMap.map(document)

    ticker.splitRatio should equal(1.5)
  }

  it should "not map the splitRatio value when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.splitRatio should be (null)
  }

  it should "map the chromosome value when present" in {

    val chromosome = "1010101010101010101010101"
    val document =  Document("chromosome" -> chromosome)

    val ticker = DocumentToTickerMap.map(document)

    ticker.chromosome should equal(chromosome)
  }

  it should "not map the chromosome value when it isn't present" in {

    val document =  Document()

    val ticker = DocumentToTickerMap.map(document)

    ticker.chromosome should be (ChromosomeDecoder.DEFAULT_CHROMOSOME)
  }
}
