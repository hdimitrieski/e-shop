package com.eshop.basket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.kafka.consumer.topic")
@Data
@RefreshScope
public class KafkaTopics {
  private String orders;
  private String orderCheckouts;
  private String productPriceChanges;
}
