package com.eshop.ordering.api.controllers;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.CancelOrderCommand;
import com.eshop.ordering.api.application.commands.CreateOrderDraftCommand;
import com.eshop.ordering.api.application.commands.ShipOrderCommand;
import com.eshop.ordering.api.application.dtos.OrderDraftDTO;
import com.eshop.ordering.api.application.queries.OrderQueries;
import com.eshop.ordering.api.application.queries.OrderViewModel;
import com.eshop.ordering.api.controllers.exceptions.BadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("orders")
@RestController
@RequiredArgsConstructor
public class OrdersController {

  private final Pipeline pipeline;
  private final OrderQueries orderQueries;

  // TODO HD check this example with pipeline, validation, ... https://github.com/sizovs/unsuck-java
  @RequestMapping(value = "cancel", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.OK)
  public void cancelOrder(
      @RequestBody CancelOrderCommand command,
      @RequestHeader("x-requestid") String requestId
  ) {
//    command.execute(pipeline);
//    var requestCancelOrder = new CancelOrderIdentifiedCommand(command, UUID.randomUUID());
//    pipeline.send(requestCancelOrder);
//    var c = new IdentifiedCommand<>(command, UUID.randomUUID());
    var result = pipeline.send(command);

    if (result != Boolean.TRUE) {
      throw new BadRequest();
    }
  }

  @RequestMapping(value = "ship", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.OK)
  public void shipOrder(
      @RequestBody ShipOrderCommand command,
      @RequestHeader("x-requestid") String requestId
  ) {
    var result = pipeline.send(command);

    if (result != Boolean.TRUE) {
      throw new BadRequest();
    }
  }

  @RequestMapping("{orderId}")
  public ResponseEntity<OrderViewModel.Order> getOrder(@PathVariable Long orderId) {
    // TODO handle not found
    return ResponseEntity.ok(orderQueries.getOrder(orderId));
  }

  @RequestMapping()
  public ResponseEntity<List<OrderViewModel.OrderSummary>> getOrders() {
    // TODO HD var userid = _identityService.GetUserIdentity();
    return ResponseEntity.ok(orderQueries.getOrdersFromUser("user-id-1"));
  }

  @RequestMapping(value = "draft", method = RequestMethod.POST)
  public ResponseEntity<OrderDraftDTO> createOrderDraftFromBasketData(
      @RequestBody CreateOrderDraftCommand command
  ) {
    return ResponseEntity.ok(pipeline.send(command));
  }

}
