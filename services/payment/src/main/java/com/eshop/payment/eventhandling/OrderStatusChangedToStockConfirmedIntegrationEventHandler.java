package com.eshop.payment.eventhandling;

import com.eshop.shared.eventhandling.EventBus;
import com.eshop.shared.eventhandling.IntegrationEvent;
import com.eshop.payment.events.OrderPaymentFailedIntegrationEvent;
import com.eshop.payment.events.OrderPaymentSucceededIntegrationEvent;
import com.eshop.payment.events.OrderStatusChangedToStockConfirmedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusChangedToStockConfirmedIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToStockConfirmedIntegrationEventHandler.class);

  private final EventBus paymentStatusEventBus;
  private boolean paymentSucceeded = true;

  @KafkaListener(groupId = "stock-confirmed-group", topics = "${spring.kafka.consumer.topic.stockConfirmed}")
  public void handle(OrderStatusChangedToStockConfirmedIntegrationEvent event) {
    logger.info("Handling integration event: {} - ({})", event.getId(), event.getClass().getSimpleName());

    IntegrationEvent orderPaymentIntegrationEvent;

    if (paymentSucceeded) {
      orderPaymentIntegrationEvent = new OrderPaymentSucceededIntegrationEvent(event.getOrderId());
      paymentSucceeded = false;
    } else {
      orderPaymentIntegrationEvent = new OrderPaymentFailedIntegrationEvent(event.getOrderId());
      paymentSucceeded = true;
    }

    paymentStatusEventBus.publish(orderPaymentIntegrationEvent);
  }
}
