package com.eshop.signaler.integrationevents.eventhandling;

import com.eshop.eventbus.IntegrationEventHandler;
import com.eshop.signaler.integrationevents.events.OrderStatusChangedToCancelledIntegrationEvent;
import com.eshop.signaler.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToCancelledIntegrationEventHandler
    implements IntegrationEventHandler<OrderStatusChangedToCancelledIntegrationEvent> {
  private final SimpMessagingTemplate simpMessagingTemplate;

  @KafkaListener(groupId = "cancelled-orders-group", topics = "${spring.kafka.consumer.topic.cancelledOrders}")
  @Override
  public void handle(OrderStatusChangedToCancelledIntegrationEvent event) {
    simpMessagingTemplate.convertAndSendToUser(
        event.getBuyerName(), "/user/topic/order-cancelled", new OrderStatus(event.getOrderId(), event.getOrderStatus()));
  }
}
