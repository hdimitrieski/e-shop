package com.eshop.signaler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.kafka.consumer.topic")
@Data
@RefreshScope
public class KafkaTopics {
  private String ordersWaitingForValidation;
  private String cancelledOrders;
  private String shippedOrders;
  private String submittedOrders;
  private String paidOrders;
  private String stockConfirmed;
}
