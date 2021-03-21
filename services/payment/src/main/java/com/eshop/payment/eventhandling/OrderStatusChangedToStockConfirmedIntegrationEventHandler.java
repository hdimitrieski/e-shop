package com.eshop.payment.eventhandling;

import com.eshop.eventbus.EventBus;
import com.eshop.eventbus.IntegrationEvent;
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

    // Business feature comment:
    // When OrderStatusChangedToStockConfirmed Integration Event is handled.
    // Here we're simulating that we'd be performing the payment against any payment gateway
    // Instead of a real payment we just take the env. var to simulate the payment
    // The payment can be successful or it can fail

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
