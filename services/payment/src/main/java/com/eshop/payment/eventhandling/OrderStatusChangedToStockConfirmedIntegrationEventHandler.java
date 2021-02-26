package com.eshop.payment.eventhandling;

import com.eshop.payment.events.IntegrationEvent;
import com.eshop.payment.events.OrderPaymentFailedIntegrationEvent;
import com.eshop.payment.events.OrderPaymentSucceededIntegrationEvent;
import com.eshop.payment.events.OrderStatusChangedToStockConfirmedIntegrationEvent;
import com.eshop.payment.infrastructure.EventBus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusChangedToStockConfirmedIntegrationEventHandler {
  private final EventBus eventBus;
  private boolean paymentSucceeded = true;

  @KafkaListener(groupId = "catalogGroup", topics = "${spring.kafka.consumer.topic.order}")
  public void handle(OrderStatusChangedToStockConfirmedIntegrationEvent event) {
    System.out.printf("----- Handling integration event: {%s} - ({%s})", event.getId(), event.getClass().getSimpleName());

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

    System.out.printf("----- Publishing integration event: {%s} - ({%s})",
        orderPaymentIntegrationEvent.getId(), orderPaymentIntegrationEvent.getClass().getSimpleName());

    eventBus.publish(orderPaymentIntegrationEvent);
  }
}
