package com.spacecorpshandbook.ticker.core.map

import java.time.{LocalDateTime, ZoneId}

import com.spacecorpshandbook.ticker.core.model.BsonMappable
import org.mongodb.scala.bson.{BsonDateTime, BsonDecimal128, BsonString}
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class BsonMappableToBsonTest extends FlatSpec
  with Matchers
  with OneInstancePerTest {

  val STRING_TO_MAP = "a mapped string value"
  val BIG_DECIMAL_TO_MAP = BigDecimal(42.0)
  val DATE_TIME_TO_MAP = LocalDateTime.now()

  behavior of "a BsonMappable when mapped to a Bson Document"

  var dummpyBsonMappable = new DummyBsonMappable(STRING_TO_MAP, BIG_DECIMAL_TO_MAP, DATE_TIME_TO_MAP)

  val document = BsonMappableToBson.map(dummpyBsonMappable)

  it should "map string values" in {

    document.get("stringValue") match {
      case Some(strVal: BsonString) => strVal.getValue should equal(STRING_TO_MAP)
      case None => true should equal(false)
    }
  }

  it should "map BigDecimal values" in {

    document.get("bigDecimalValue") match {
      case Some(bsonDecimalValue: BsonDecimal128) =>
        bsonDecimalValue.getValue should equal(BsonDecimal128(42.0).getValue)
      case None => true should equal(false)
    }
  }

  it should "map LocalDateTime values" in {

    val expectedDateTimeAsMilli = DATE_TIME_TO_MAP.atZone(ZoneId.of("UTC")).toInstant.toEpochMilli

    document.get("dateTimeValue") match {
      case Some(dateTimeValue: BsonDateTime) => dateTimeValue.getValue should equal(expectedDateTimeAsMilli)
      case None => true should equal(false)
    }
  }
}

class DummyBsonMappable(var stringValue: String,
                        var bigDecimalValue: BigDecimal,
                        var dateTimeValue: LocalDateTime)
  extends BsonMappable[DummyBsonMappable] {

}
