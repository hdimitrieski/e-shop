package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.api.application.integrationevents.OrderingIntegrationEventService;
import com.eshop.ordering.api.application.integrationevents.events.OrderStartedIntegrationEvent;
import com.eshop.ordering.domain.aggregatesmodel.order.Address;
import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class CreateOrderCommandHandler implements Command.Handler<CreateOrderCommand, Boolean> {
  private static final Logger logger = LoggerFactory.getLogger(CreateOrderCommandHandler.class);

  private final OrderRepository orderRepository;
  private final OrderingIntegrationEventService orderingIntegrationEventService;
  @Value("${spring.kafka.consumer.topic.orders}")
  private String ordersTopic;

  @Transactional
  @Override
  public Boolean handle(CreateOrderCommand command) {
    // Add Integration event to clean the basket
    var orderStartedIntegrationEvent = new OrderStartedIntegrationEvent(command.getUserId());
    orderingIntegrationEventService.addAndSaveEvent(ordersTopic, orderStartedIntegrationEvent);

    // Add/Update the Buyer AggregateRoot
    // DDD patterns comment: Add child entities and value-objects through the Order Aggregate-Root
    // methods and constructor so validations, invariants and business logic
    // make sure that consistency is preserved across the whole aggregate
    var address = new Address(command.getStreet(), command.getCity(), command.getState(), command.getCountry(),
        command.getZipCode());
    var order = new Order(command.getUserId(), command.getUserName(), address, command.getCardTypeId(),
        command.getCardNumber(), command.getCardSecurityNumber(), command.getCardHolderName(),
        command.getCardExpiration());

    for (var item : command.getOrderItems()) {
      order.addOrderItem(item.productId(), item.productName(), item.unitPrice(), item.discount(),
          item.pictureUrl(), item.units());
    }

    logger.info("Creating Order");

    orderRepository.save(order);

    return true;
  }

}
