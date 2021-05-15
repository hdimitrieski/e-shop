package com.eshop.signaler.integrationevents.eventhandling;

import com.eshop.shared.eventhandling.IntegrationEventHandler;
import com.eshop.signaler.integrationevents.events.OrderStatusChangedToAwaitingValidationIntegrationEvent;
import com.eshop.signaler.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToAwaitingValidationIntegrationEventHandler
    implements IntegrationEventHandler<OrderStatusChangedToAwaitingValidationIntegrationEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  private final SimpMessagingTemplate simpMessagingTemplate;

  @KafkaListener(groupId = "orders-waiting-validation-group-2", topics = "${spring.kafka.consumer.topic.ordersWaitingForValidation}")
  @Override
  public void handle(OrderStatusChangedToAwaitingValidationIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    simpMessagingTemplate.convertAndSendToUser(
        event.getBuyerName(), "/queue/order-waiting-validation", new OrderStatus(event.getOrderId(), event.getOrderStatus()));
  }
}
