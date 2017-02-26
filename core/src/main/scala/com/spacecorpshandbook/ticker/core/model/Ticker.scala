package com.spacecorpshandbook.ticker.core.model

import scala.beans.BeanProperty
import scala.reflect.runtime.{universe => ru}

/**
  * Stock ticker resource type
  */
class Ticker extends BsonMappable[Ticker] {

  @BeanProperty
  var id: String = ""

  @BeanProperty
  var ticker: String = ""

  @BeanProperty
  var date: String = ""

  @BeanProperty
  var open: Double = _

  @BeanProperty
  var close: Double = _

  @BeanProperty
  var high: Double = _

  @BeanProperty
  var low: Double = _

  @BeanProperty
  var chromosome: String = ""

}