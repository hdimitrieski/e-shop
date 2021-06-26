package com.eshop.ordering.config;

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
  private String ordersWaitingForValidation;
  private String cancelledOrders;
  private String shippedOrders;
  private String submittedOrders;
  private String paidOrders;
  private String orderStockConfirmed;
  private String orderStockRejected;
  private String gracePeriodConfirmed;
  private String paymentStatus;
  private String stockConfirmed;
}
