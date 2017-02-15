package com.spacecorpshandbook.ticker.core.map

import com.spacecorpshandbook.ticker.core.model.BsonMappable
import org.mongodb.scala.Document
import org.mongodb.scala.bson.BsonDouble
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.beans.BeanProperty

class BsonToBsonMappableTest extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  var dummyMappable: AbsonMappableDummy = _

  before {
    dummyMappable = new AbsonMappableDummy
  }

  behavior of "Bson to BsonMappable"

  it should "map all fields from the BsonDocument to the corresponding fields of the BsonMappable object" in {

    val expectedFooValue = "bar"
    val expectedBarValue = 33
    val expectedDadValue = "omicron persei 8"
    val bsonDoc = Document("foo" -> expectedFooValue, "bar" -> expectedBarValue, "dad" -> expectedDadValue)

    BsonToBsonMappable.map(bsonDoc, dummyMappable)

    dummyMappable.foo should equal(expectedFooValue)
    dummyMappable.bar should equal(expectedBarValue)
    dummyMappable.dad should equal(expectedDadValue)
  }

  it should "ignore supported BSON type" in {

    val doubleVal: BsonDouble = new BsonDouble(2.333)
    val bsonDoc = Document("dVal" -> doubleVal)

    BsonToBsonMappable.map(bsonDoc, dummyMappable)
  }

  it should "ignore BSON fields not present in target object" in {

    val bsonDoc = Document("IdoNotExist" -> "blah")

    BsonToBsonMappable.map(bsonDoc, dummyMappable)
  }
}

class AbsonMappableDummy extends BsonMappable[AbsonMappableDummy] {

  @BeanProperty
  var foo: String = ""

  @BeanProperty
  var bar: Int = 0

  @beans.BeanProperty
  var dad: String = ""

  var dVal: Double = _
}