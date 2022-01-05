package com.eshop.basket.controller;

import com.eshop.basket.model.BasketCheckout;
import com.eshop.basket.model.CustomerBasket;
import com.eshop.basket.services.BasketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController()
@RequestMapping("basket")
@RequiredArgsConstructor
public class BasketController {
  private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

  private final BasketService basketService;

  @RequestMapping("/customer/{customerId}")
  public ResponseEntity<CustomerBasket> getBasketByCustomerId(@PathVariable String customerId) {
    logger.info("Find basket from user: {}", customerId);
    return ResponseEntity.ok(basketService.findByCustomerId(customerId));
  }
  @RequestMapping("{basketId}")
  public ResponseEntity<CustomerBasket> getBasketById(@PathVariable UUID basketId) {
    logger.info("Find basket by id: {}", basketId);
    return ResponseEntity.of(basketService.findById(basketId));
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<CustomerBasket> updateBasket(@RequestBody @Valid CustomerBasket basket) {
    logger.info("Update basket from user: {}", basket.getBuyerId());
    return ResponseEntity.ok(basketService.updateBasket(basket));
  }

  @RequestMapping(path = "checkout", method = RequestMethod.POST)
  public void checkout(
    @RequestBody @Valid BasketCheckout basketCheckout,
    @RequestHeader("x-requestid") String requestId
  ) {
    logger.info("Checkout basket for user: {}", basketCheckout.getBuyer());
    setRequestId(basketCheckout, requestId);
    basketService.checkout(basketCheckout);
  }

  @RequestMapping(value = "{basketId}", method = RequestMethod.DELETE)
  public void deleteBasket(@PathVariable UUID basketId) {
    basketService.delete(basketId);
  }

  public void setRequestId(BasketCheckout basketCheckout, String requestId) {
    UUID requestIdUuid;

    try {
      requestIdUuid = UUID.fromString(requestId);
    } catch (IllegalArgumentException e) {
      requestIdUuid = basketCheckout.getRequestId();
    }

    basketCheckout.setRequestId(requestIdUuid);
  }

}
