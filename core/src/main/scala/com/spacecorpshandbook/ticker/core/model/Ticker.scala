package com.spacecorpshandbook.ticker.core.model

import java.time.LocalDate

import com.fasterxml.jackson.annotation.JsonProperty
import com.spacecorpshandbook.ticker.core.chromosome.ChromosomeDecoder

import scala.beans.BeanProperty

/**
  * Stock ticker resource type
  */
class Ticker {

  @BeanProperty
  @JsonProperty( value = "_id" )
  var id: String = ""

  @BeanProperty
  var ticker: String = ""

  @BeanProperty
  var date: LocalDate = _

  @BeanProperty
  var open: BigDecimal = _

  @BeanProperty
  var close: BigDecimal = _

  @BeanProperty
  var high: BigDecimal = _

  @BeanProperty
  var low: BigDecimal = _

  @BeanProperty
  var volume: BigDecimal = _

  @BeanProperty
  @JsonProperty( value = "ex-dividend")
  var exDividend: BigDecimal = _

  @BeanProperty
  @JsonProperty( value = "split_ratio")
  var splitRatio: BigDecimal = _

  @BeanProperty
  var chromosome: String = ChromosomeDecoder.DEFAULT_CHROMOSOME

}
