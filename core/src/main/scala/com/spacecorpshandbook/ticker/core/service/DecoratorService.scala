package com.spacecorpshandbook.ticker.core.service

import com.spacecorpshandbook.ticker.core.model.Ticker

/**
  * Represents the various ways to decorate a Ticker
  */
case class DecoratorService() {

  /**
    *
    * Update the provided ticker with a chromosome
    *
    * @param ticker
    * @return ticker updated with a chromosome
    */
  def addChromosome(ticker: Ticker): Ticker = {

    return new Ticker
  }

}
