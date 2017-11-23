package com.spacecorpshandbook.ticker.spring.exception

case class InvalidTickerException(ticker: String) extends RuntimeException(s"""invalid ticker was ${ticker}""") {

}
