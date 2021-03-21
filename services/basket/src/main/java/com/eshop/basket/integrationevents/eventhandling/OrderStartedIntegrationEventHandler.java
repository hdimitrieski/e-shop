package com.eshop.basket.integrationevents.eventhandling;

import com.eshop.basket.integrationevents.events.OrderStartedIntegrationEvent;
import com.eshop.basket.model.BasketRepository;
import com.eshop.eventbus.IntegrationEventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderStartedIntegrationEventHandler implements IntegrationEventHandler<OrderStartedIntegrationEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderStartedIntegrationEventHandler.class);

  private final BasketRepository basketRepository;

  @KafkaListener(groupId = "orders-group", topics = "${spring.kafka.consumer.topic.orders}")
  @Override
  public void handle(OrderStartedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    basketRepository.deleteBasket(event.getUserId());
  }
}
