package com.eshop.basket.services;

import com.eshop.basket.controller.BasketController;
import com.eshop.basket.integrationevents.events.UserCheckoutAcceptedIntegrationEvent;
import com.eshop.basket.model.BasketCheckout;
import com.eshop.basket.model.BasketRepository;
import com.eshop.basket.model.CustomerBasket;
import com.eshop.error.NotFoundException;
import com.eshop.eventbus.EventBus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {
  private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

  private final BasketRepository basketRepository;
  private final IdentityService identityService;
  private final EventBus orderCheckoutsEventBus;

  @Override
  public CustomerBasket getBasketById(String customerId) {
    return getCustomerBasket(customerId);
  }

  @Override
  public CustomerBasket updateBasket(CustomerBasket basket) {
    return basketRepository.updateBasket(basket);
  }

  @Override
  public void checkout(BasketCheckout basketCheckout) {
    var userId = identityService.getUserName();
    var basket = getCustomerBasket(userId);
    var userName = identityService.getUserName();

    logger.info("Checking out the basket for user: {} - request id: {}", userName, basketCheckout.getRequestId());

    var event = new UserCheckoutAcceptedIntegrationEvent(
        userId,
        userName,
        basketCheckout.getCity(),
        basketCheckout.getStreet(),
        basketCheckout.getState(),
        basketCheckout.getCountry(),
        basketCheckout.getZipCode(),
        basketCheckout.getCardNumber(),
        basketCheckout.getCardHolderName(),
        basketCheckout.getCardExpiration(),
        basketCheckout.getCardSecurityNumber(),
        basketCheckout.getCardTypeId(),
        basketCheckout.getBuyer(),
        basketCheckout.getRequestId(),
        basket
    );

    // Once basket is checkout, sends an integration event to
    // ordering.api to convert basket to order and proceeds with
    // order creation process
    orderCheckoutsEventBus.publish(event);
  }

  @Override
  public void deleteBasketForCustomer(String customerId) {
    basketRepository.deleteBasket(customerId);
  }

  private CustomerBasket getCustomerBasket(String customerId) {
    return basketRepository.getBasket(customerId)
        .orElseThrow(() -> new NotFoundException("Basket is not found for user %s".formatted(customerId)));
  }
}
