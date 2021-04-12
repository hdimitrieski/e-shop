package com.eshop.signaler.integrationevents.eventhandling;

import com.eshop.eventbus.IntegrationEventHandler;
import com.eshop.signaler.integrationevents.events.OrderStatusChangedToPaidIntegrationEvent;
import com.eshop.signaler.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToPaidIntegrationEventHandler
    implements IntegrationEventHandler<OrderStatusChangedToPaidIntegrationEvent> {
  private final SimpMessagingTemplate simpMessagingTemplate;

  @KafkaListener(groupId = "paid-orders-group", topics = "${spring.kafka.consumer.topic.paidOrders}")
  @Override
  public void handle(OrderStatusChangedToPaidIntegrationEvent event) {
    simpMessagingTemplate.convertAndSendToUser(
        event.getBuyerName(), "/user/topic/order-paid", new OrderStatus(event.getOrderId(), event.getOrderStatus()));
  }
}
