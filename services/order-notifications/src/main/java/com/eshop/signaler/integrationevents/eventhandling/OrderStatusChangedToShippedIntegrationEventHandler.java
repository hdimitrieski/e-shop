package com.eshop.signaler.integrationevents.eventhandling;

import com.eshop.eventbus.IntegrationEventHandler;
import com.eshop.signaler.integrationevents.events.OrderStatusChangedToShippedIntegrationEvent;
import com.eshop.signaler.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToShippedIntegrationEventHandler
    implements IntegrationEventHandler<OrderStatusChangedToShippedIntegrationEvent> {
  private final SimpMessagingTemplate simpMessagingTemplate;

  @KafkaListener(groupId = "shipped-orders-group", topics = "${spring.kafka.consumer.topic.shippedOrders}")
  @Override
  public void handle(OrderStatusChangedToShippedIntegrationEvent event) {
    simpMessagingTemplate.convertAndSendToUser(
        event.getBuyerName(), "/user/topic/order-shipped", new OrderStatus(event.getOrderId(), event.getOrderStatus()));
  }
}
