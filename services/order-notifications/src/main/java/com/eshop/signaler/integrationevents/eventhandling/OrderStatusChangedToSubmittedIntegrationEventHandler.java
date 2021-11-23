package com.eshop.signaler.integrationevents.eventhandling;

import com.eshop.shared.eventhandling.IntegrationEventHandler;
import com.eshop.signaler.integrationevents.events.OrderStatusChangedToSubmittedIntegrationEvent;
import com.eshop.signaler.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToSubmittedIntegrationEventHandler
    implements IntegrationEventHandler<OrderStatusChangedToSubmittedIntegrationEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToSubmittedIntegrationEventHandler.class);
  private static final String DESTINATION = "/queue/order-submitted";

  private final SimpMessagingTemplate simpMessagingTemplate;

  @KafkaListener(
      groupId = "${app.kafka.group.submittedOrders}",
      topics = "${spring.kafka.consumer.topic.submittedOrders}"
  )
  @Override
  public void handle(OrderStatusChangedToSubmittedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    simpMessagingTemplate.convertAndSendToUser(
        event.getBuyerName(),
        DESTINATION,
        new OrderStatus(event.getOrderId(), event.getOrderStatus())
    );
  }
}
