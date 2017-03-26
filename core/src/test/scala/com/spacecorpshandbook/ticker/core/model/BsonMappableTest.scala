package com.spacecorpshandbook.ticker.core.model

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.beans.BeanProperty
import scala.reflect.runtime.{universe => ru}

class BsonMappableTest extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  var mappable: AbsonMappableDummy = _
  var dumboMappable: AbsonMappableDumbo = _

  before {

    mappable = new AbsonMappableDummy

    dumboMappable = new AbsonMappableDumbo

  }

  behavior of "A BsonMappable type"

  it should "store the child type in a hash map" in {

    val declsForType = mappable.getMappableFields(ru.typeOf[AbsonMappableDummy].hashCode)

    declsForType should not be empty
  }

  it should "have a mappable field for each BeanProperty" in {

    val declsForType = mappable.getMappableFields(ru.typeOf[AbsonMappableDummy].hashCode)

    declsForType should have length 3
    declsForType.filter(_.name.toString equals "a") should have length 1
    declsForType.filter(_.name.toString equals "b") should have length 1
    declsForType.filter(_.name.toString equals "c") should have length 1
  }

  it should "only update the map once for each type" in {

    new AbsonMappableDummy
    new AbsonMappableDummy
    new AbsonMappableDummy
    new AbsonMappableDummy
    new AbsonMappableDummy

    val declsForType = mappable.getMappableFields(ru.typeOf[AbsonMappableDummy].hashCode)

    declsForType should have length 3
  }

  it should "store a child type for each instantiated sub-type" in {

    dumboMappable.getMappableFields.size should be(3)
  }

  it should "return method symbols for a concrete type" in {

    val declsForType = mappable.getMyTypeMappableFields

    declsForType should have length 3
  }
}


class AbsonMappableDumbo extends BsonMappable[AbsonMappableDumbo] {

  @BeanProperty
  var mickey: String = ""

  @BeanProperty
  var donald: Int = 0

  @beans.BeanProperty
  var goofy: Seq[AnyRef] = Seq()
}

class AbsonMappableDummy extends BsonMappable[AbsonMappableDummy] {

  @BeanProperty
  var a : String = ""

  @BeanProperty
  var b: Int = 0

  @beans.BeanProperty
  var c: Seq[AnyRef] = Seq()
}
