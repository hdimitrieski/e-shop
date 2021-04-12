package com.eshop.signaler.integrationevents.eventhandling;

import com.eshop.eventbus.IntegrationEventHandler;
import com.eshop.signaler.integrationevents.events.OrderStatusChangedToAwaitingValidationIntegrationEvent;
import com.eshop.signaler.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToAwaitingValidationIntegrationEventHandler
    implements IntegrationEventHandler<OrderStatusChangedToAwaitingValidationIntegrationEvent> {

  private final SimpMessagingTemplate simpMessagingTemplate;

  @KafkaListener(groupId = "orders-waiting-validation-group", topics = "${spring.kafka.consumer.topic.ordersWaitingForValidation}")
  @Override
  public void handle(OrderStatusChangedToAwaitingValidationIntegrationEvent event) {
    simpMessagingTemplate.convertAndSendToUser(
        event.getBuyerName(), "/user/topic/order-waiting-validation", new OrderStatus(event.getOrderId(), event.getOrderStatus()));
  }
}
