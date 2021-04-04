package com.eshop.payment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.kafka.consumer.topic")
@Data
@RefreshScope
public class KafkaTopics {
  private String paymentStatus;
  private String stockConfirmed;
}
