package com.eshop.ordering.api.application.integrationevents.eventhandling;

import com.eshop.ordering.api.application.commands.CreateOrderCommand;
import com.eshop.ordering.api.application.commands.CreateOrderIdentifiedCommand;
import com.eshop.ordering.api.application.integrationevents.events.UserCheckoutAcceptedIntegrationEvent;
import com.eshop.ordering.api.infrastructure.commandbus.CommandBus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCheckoutAcceptedIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(UserCheckoutAcceptedIntegrationEventHandler.class);

  private final CommandBus commandBus;

  /**
   * Integration event handler which starts the create order process.
   *
   * @param event Integration event message which is sent by the  basket.api once it has successfully process the
   *              order items.
   */
  @KafkaListener(groupId = "order-checkouts-group", topics = "${spring.kafka.consumer.topic.orderCheckouts}")
  public void handle(UserCheckoutAcceptedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    var result = false;

    if (event.getRequestId() != null) {
      var createOrderCommand = new CreateOrderCommand(
          event.getBasket().getItems(), event.getUserId(), event.getUserName(), event.getCity(),
          event.getStreet(), event.getState(), event.getCountry(), event.getZipCode(),
          event.getCardNumber(), event.getCardHolderName(), event.getCardExpiration(),
          event.getCardSecurityNumber(), event.getCardTypeId());
      var requestCreateOrder = new CreateOrderIdentifiedCommand(createOrderCommand, event.getRequestId());
      result = commandBus.send(requestCreateOrder);

      if (result) {
        logger.info("CreateOrderCommand succeeded - RequestId: {}", event.getRequestId());
      } else {
        logger.info("CreateOrderCommand failed - RequestId: {}", event.getRequestId());
      }

    } else {
      logger.info("Invalid IntegrationEvent - RequestId is missing - {}", event.getClass().getSimpleName());
    }
  }
}
