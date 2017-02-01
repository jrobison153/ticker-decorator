package com.spacecorpshandbook.ticker.spring

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.{ComponentScan, Configuration}

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
class TickerDecoratorAppConfig {

}
