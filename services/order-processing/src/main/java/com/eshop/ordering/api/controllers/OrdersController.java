package com.eshop.ordering.api.controllers;

import com.eshop.ordering.api.application.commands.*;
import com.eshop.ordering.api.application.dtos.OrderDraftDTO;
import com.eshop.ordering.api.application.queries.OrderQueries;
import com.eshop.ordering.api.application.queries.OrderViewModel;
import com.eshop.ordering.api.application.services.IdentityService;
import com.eshop.ordering.api.infrastructure.commandbus.CommandBus;
import com.eshop.ordering.api.infrastructure.exceptions.UnauthorizedException;
import com.eshop.shared.rest.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("orders")
@RestController
@RequiredArgsConstructor
@Validated
public class OrdersController {

  private final CommandBus commandBus;
  private final OrderQueries orderQueries;
  private final IdentityService identityService;

  @RequestMapping(value = "cancel", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.OK)
  public void cancelOrder(
    @RequestBody @Valid CancelOrderCommand command,
    @RequestHeader("x-requestid") String requestId
  ) {
    var requestCancelOrder = new CancelOrderIdentifiedCommand(command, UUID.fromString(requestId));
    commandBus.send(requestCancelOrder);
  }

  @RequestMapping(value = "ship", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.OK)
  public void shipOrder(
    @RequestBody @Valid ShipOrderCommand command,
    @RequestHeader("x-requestid") String requestId
  ) {
    var shipOrderCommand = new ShipOrderIdentifiedCommand(command, UUID.fromString(requestId));
    commandBus.send(shipOrderCommand);
  }

  @RequestMapping("{orderId}")
  public ResponseEntity<OrderViewModel.Order> getOrder(@PathVariable String orderId) {
    final var order = orderQueries.getOrder(orderId)
      .orElseThrow(() -> new NotFoundException("Order %s not found".formatted(orderId)));

    if (!order.ownerId().equals(identityService.getUserIdentity()) && !identityService.isAdmin()) {
      throw new UnauthorizedException();
    }

    return ResponseEntity.ok(order);
  }

  @RequestMapping()
  public ResponseEntity<List<OrderViewModel.Order>> getAllOrders() {
    return ResponseEntity.ok(orderQueries.allOrders());
  }

  @RequestMapping("user")
  public ResponseEntity<List<OrderViewModel.Order>> getUserOrders() {
    return ResponseEntity.ok(orderQueries.userOrders(identityService.getUserIdentity()));
  }

  @RequestMapping("summaries")
  public ResponseEntity<List<OrderViewModel.OrderSummary>> getUserOrderSummaries() {
    return ResponseEntity.ok(orderQueries.getOrdersFromUser(identityService.getUserIdentity()));
  }

  @RequestMapping(value = "draft", method = RequestMethod.POST)
  public ResponseEntity<OrderDraftDTO> createOrderDraftFromBasketData(
    @RequestBody @Valid CreateOrderDraftCommand command
  ) {
    return ResponseEntity.ok(commandBus.send(command));
  }

}
