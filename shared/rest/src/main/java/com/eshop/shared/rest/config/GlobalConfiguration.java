package com.eshop.shared.rest.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfiguration {

  /**
   * This bean is for Java 14 record to be serialized as JSON
   *
   * @return Jackson2ObjectMapperBuilderCustomizer builder
   */
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
    return builder ->
        builder
            .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
  }
}
