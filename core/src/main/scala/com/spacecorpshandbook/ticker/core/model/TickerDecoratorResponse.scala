package com.spacecorpshandbook.ticker.core.model

import scala.beans.BeanProperty

/**
  * Represents the base response object from the ticker decorator application
  */
class TickerDecoratorResponse {

  @BeanProperty
  var message: String = ""
}
