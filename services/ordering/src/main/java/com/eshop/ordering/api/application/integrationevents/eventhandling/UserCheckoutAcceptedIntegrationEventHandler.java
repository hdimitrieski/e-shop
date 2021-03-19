package com.eshop.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.CreateOrderCommand;
import com.eshop.ordering.api.application.integrationevents.events.UserCheckoutAcceptedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCheckoutAcceptedIntegrationEventHandler {
  private final Pipeline pipeline;

  /**
   * Integration event handler which starts the create order process.
   *
   * @param event Integration event message which is sent by the  basket.api once it has successfully process the
   *              order items.
   */
  @KafkaListener(groupId = "order-checkouts-group", topics = "${spring.kafka.consumer.topic.orderCheckouts}")
  public void handle(UserCheckoutAcceptedIntegrationEvent event) {
    System.out.printf("----- Handling integration event: {%s} - (%s})", event.getId(), event.getClass().getSimpleName());
    var result = false;

    if (event.getRequestId() != null) {
      var createOrderCommand = new CreateOrderCommand(
          event.getBasket().getItems(), event.getUserId(), event.getUserName(), event.getCity(),
          event.getStreet(), event.getState(), event.getCountry(), event.getZipCode(),
          event.getCardNumber(), event.getCardHolderName(), event.getCardExpiration(),
          event.getCardSecurityNumber(), event.getCardTypeId());
//      var requestCreateOrder = new IdentifiedCommand<CreateOrderCommand, Boolean>(createOrderCommand, event.getRequestId());
      result = pipeline.send(createOrderCommand);

      if (result) {
        System.out.printf("----- CreateOrderCommand succeeded - RequestId: {%s}\n", event.getRequestId());
      } else {
        System.out.printf("CreateOrderCommand failed - RequestId: {%s}\n", event.getRequestId());
      }

    } else {
      System.out.printf("Invalid IntegrationEvent - RequestId is missing - {%s}", event.getClass().getSimpleName());
    }
  }
}
