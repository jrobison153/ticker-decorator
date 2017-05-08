package com.spacecorpshandbook.ticker.core.model

import java.time.LocalDateTime

import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeDecoder

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
  var date: LocalDateTime = _

  @BeanProperty
  var open: BigDecimal = _

  @BeanProperty
  var close: BigDecimal = _

  @BeanProperty
  var high: BigDecimal = _

  @BeanProperty
  var low: BigDecimal = _

  @BeanProperty
  var chromosome: String = ChromosomeDecoder.DEFAULT_CHROMOSOME

}