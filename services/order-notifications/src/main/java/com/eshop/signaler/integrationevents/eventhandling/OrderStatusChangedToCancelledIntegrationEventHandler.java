package com.eshop.signaler.integrationevents.eventhandling;

import com.eshop.shared.eventhandling.IntegrationEventHandler;
import com.eshop.signaler.integrationevents.events.OrderStatusChangedToCancelledIntegrationEvent;
import com.eshop.signaler.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToCancelledIntegrationEventHandler
    implements IntegrationEventHandler<OrderStatusChangedToCancelledIntegrationEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToCancelledIntegrationEventHandler.class);
  private static final String DESTINATION = "/queue/order-cancelled";
  private final SimpMessagingTemplate simpMessagingTemplate;

  @KafkaListener(
      groupId = "${app.kafka.group.cancelledOrders}",
      topics = "${spring.kafka.consumer.topic.cancelledOrders}"
  )
  @Override
  public void handle(OrderStatusChangedToCancelledIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    simpMessagingTemplate.convertAndSendToUser(
        event.getBuyerName(),
        DESTINATION,
        new OrderStatus(event.getOrderId(), event.getOrderStatus())
    );
  }
}
