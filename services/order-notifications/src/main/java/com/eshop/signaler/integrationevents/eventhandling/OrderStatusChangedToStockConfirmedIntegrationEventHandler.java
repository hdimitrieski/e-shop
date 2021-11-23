package com.eshop.signaler.integrationevents.eventhandling;

import com.eshop.shared.eventhandling.IntegrationEventHandler;
import com.eshop.signaler.integrationevents.events.OrderStatusChangedToStockConfirmedIntegrationEvent;
import com.eshop.signaler.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToStockConfirmedIntegrationEventHandler
    implements IntegrationEventHandler<OrderStatusChangedToStockConfirmedIntegrationEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToStockConfirmedIntegrationEventHandler.class);
  private static final String DESTINATION = "/queue/order-stock-confirmed";

  private final SimpMessagingTemplate simpMessagingTemplate;

  @KafkaListener(
      groupId = "${app.kafka.group.stockConfirmed}",
      topics = "${spring.kafka.consumer.topic.stockConfirmed}"
  )
  @Override
  public void handle(OrderStatusChangedToStockConfirmedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    simpMessagingTemplate.convertAndSendToUser(
        event.getBuyerName(),
        DESTINATION,
        new OrderStatus(event.getOrderId(), event.getOrderStatus())
    );
  }
}
