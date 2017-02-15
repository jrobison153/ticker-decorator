package com.spacecorpshandbook.ticker.core.model

import scala.beans.BeanProperty
import scala.reflect.runtime.{universe => ru}

/**
  * Stock ticker resource type
  */
class Ticker extends BsonMappable[Ticker] {

  @BeanProperty
  var ticker: String = ""

  @BeanProperty
  var date: String = ""

  @BeanProperty
  var open: BigDecimal = _

  @BeanProperty
  var close: BigDecimal = _

  @BeanProperty
  var high: BigDecimal = _

  @BeanProperty
  var low: BigDecimal = _

  @BeanProperty
  var chromosome: String = ""

}