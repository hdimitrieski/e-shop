package com.eshop.analytics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaConfig {
  @Bean
  public RecordMessageConverter converter() {
    return new StringJsonMessageConverter();
  }
}
