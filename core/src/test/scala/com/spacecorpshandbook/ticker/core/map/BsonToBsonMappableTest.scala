package com.spacecorpshandbook.ticker.core.map

import com.spacecorpshandbook.ticker.core.model.BsonMappable
import org.bson.BsonObjectId
import org.bson.types.ObjectId
import org.mongodb.scala.Document
import org.mongodb.scala.bson.{BsonDouble, BsonJavaScript}
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

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
    val expectedDval = new BsonDouble(44.3)
    val expectedId = new ObjectId()
    val expectedObjectId = new BsonObjectId(expectedId)

    val bsonDoc = Document(
      "foo" -> expectedFooValue,
      "bar" -> expectedBarValue,
      "dad" -> expectedDadValue,
      "dVal" -> expectedDval,
      "id" -> expectedObjectId
    )

    BsonToBsonMappable.map(bsonDoc, dummyMappable)

    dummyMappable.foo should equal(expectedFooValue)
    dummyMappable.bar should equal(expectedBarValue)
    dummyMappable.dad should equal(expectedDadValue)
    dummyMappable.dVal should equal(expectedDval.getValue)
    dummyMappable.id should equal(expectedId.toHexString)
  }

  it should "ignore supported BSON type" in {

    val unsuppotedType: BsonJavaScript = new BsonJavaScript("let x = 3")
    val bsonDoc = Document("uVal" -> unsuppotedType)

    BsonToBsonMappable.map(bsonDoc, dummyMappable)
  }

  it should "ignore BSON fields not present in target object" in {

    val bsonDoc = Document("IdoNotExist" -> "blah")

    BsonToBsonMappable.map(bsonDoc, dummyMappable)
  }
}

class AbsonMappableDummy extends BsonMappable[AbsonMappableDummy] {

  var id: String = ""

  var foo: String = ""

  var bar: Int = 0

  var dad: String = ""

  var dVal: Double = _

  var uVal: String = _
}