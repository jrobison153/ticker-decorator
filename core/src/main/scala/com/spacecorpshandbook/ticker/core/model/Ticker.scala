package com.spacecorpshandbook.ticker.core.model

import scala.beans.BeanProperty

/**
  * Stock ticker resource type
  */
class Ticker {

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
