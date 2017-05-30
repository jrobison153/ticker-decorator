package com.spacecorpshandbook.ticker.spring.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.{Bean, Configuration, Primary}
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class JacksonConfig {

  @Bean
  @Primary
  def objectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper = {

    val objectMapper: ObjectMapper = new ObjectMapper().findAndRegisterModules
    objectMapper
  }
}
