package com.spacecorpshandbook.ticker.spring

import org.springframework.boot.builder.SpringApplicationBuilder

object TickerDecoratorApp {

  def main(args: Array[String]) {

    /*
      TODO: don't think args.toString accomplishes what I need but it does fix the compile error for now
     */
    new SpringApplicationBuilder(classOf[TickerDecoratorAppConfig]).web(true).run(args.toString)
  }
}