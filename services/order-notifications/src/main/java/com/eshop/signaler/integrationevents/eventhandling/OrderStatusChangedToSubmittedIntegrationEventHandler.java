package com.eshop.signaler.integrationevents.eventhandling;

import com.eshop.eventbus.IntegrationEventHandler;
import com.eshop.signaler.integrationevents.events.OrderStatusChangedToSubmittedIntegrationEvent;
import com.eshop.signaler.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToSubmittedIntegrationEventHandler
    implements IntegrationEventHandler<OrderStatusChangedToSubmittedIntegrationEvent> {
  private final SimpMessagingTemplate simpMessagingTemplate;

  @KafkaListener(groupId = "submitted-orders-group", topics = "${spring.kafka.consumer.topic.submittedOrders}")
  @Override
  public void handle(OrderStatusChangedToSubmittedIntegrationEvent event) {
    simpMessagingTemplate.convertAndSendToUser(
        event.getBuyerName(), "/user/topic/order-submitted", new OrderStatus(event.getOrderId(), event.getOrderStatus()));
  }
}
