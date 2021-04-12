package com.eshop.signaler.integrationevents.eventhandling;

import com.eshop.eventbus.IntegrationEventHandler;
import com.eshop.signaler.integrationevents.events.OrderStatusChangedToStockConfirmedIntegrationEvent;
import com.eshop.signaler.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToStockConfirmedIntegrationEventHandler
    implements IntegrationEventHandler<OrderStatusChangedToStockConfirmedIntegrationEvent> {
  private final SimpMessagingTemplate simpMessagingTemplate;

  @KafkaListener(groupId = "stock-confirmed-group", topics = "${spring.kafka.consumer.topic.stockConfirmed}")
  @Override
  public void handle(OrderStatusChangedToStockConfirmedIntegrationEvent event) {
    simpMessagingTemplate.convertAndSendToUser(
        event.getBuyerName(), "/user/topic/order-stock-confirmed", new OrderStatus(event.getOrderId(), event.getOrderStatus()));
  }
}
