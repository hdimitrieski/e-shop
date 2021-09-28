package com.eshop.catalog.config;

import org.axonframework.extensions.kafka.KafkaProperties;
import org.axonframework.extensions.kafka.eventhandling.producer.ConfirmationMode;
import org.axonframework.extensions.kafka.eventhandling.producer.DefaultProducerFactory;
import org.axonframework.extensions.kafka.eventhandling.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonKafkaConfig {

  /**
   * Creates a Kafka producer factory, using the Kafka properties configured in application.yml.
   */
  @Bean
  public ProducerFactory<String, byte[]> producerFactory(KafkaProperties kafkaProperties) {
    return DefaultProducerFactory.<String, byte[]> builder()
        .configuration(kafkaProperties.buildProducerProperties())
//        .producerCacheSize(10_000)
        .confirmationMode(ConfirmationMode.WAIT_FOR_ACK)
        .build();
  }

}
