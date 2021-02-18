package com.eshop.ordering.api.controllers;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.CancelOrderCommand;
import com.eshop.ordering.api.application.commands.CreateOrderDraftCommand;
import com.eshop.ordering.api.application.commands.ShipOrderCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("orders")
@RestController
@RequiredArgsConstructor
public class OrdersController {

  private final Pipeline pipeline;
  // TODO HD check this example with pipeline, validation, ... https://github.com/sizovs/unsuck-java
  @RequestMapping(value = "cancel", method = RequestMethod.PUT)
  public void cancelOrder(
      @RequestBody CancelOrderCommand command,
      @RequestHeader("x-requestid") String requestId
  ) {
    command.execute(pipeline);
  }

  @RequestMapping(value = "ship", method = RequestMethod.PUT)
  public void shipOrder(
      @RequestBody ShipOrderCommand command,
      @RequestHeader("x-requestid") String requestId
  ) {

  }

  @RequestMapping("{orderId}")
  public void getOrder(
      @PathVariable Integer orderId
  ) {

  }

  @RequestMapping()
  public void getOrders() {

  }

  @RequestMapping(value = "draft", method = RequestMethod.POST)
  public void createOrderDraftFromBasketData(
      @RequestBody CreateOrderDraftCommand command
  ) {

  }

}
