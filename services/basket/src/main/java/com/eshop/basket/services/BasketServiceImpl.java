package com.eshop.basket.services;

import com.eshop.basket.controller.BasketController;
import com.eshop.basket.integrationevents.events.UserCheckoutAcceptedIntegrationEvent;
import com.eshop.basket.model.BasketCheckout;
import com.eshop.basket.model.BasketRepository;
import com.eshop.basket.model.BasketStatus;
import com.eshop.basket.model.CustomerBasket;
import com.eshop.shared.eventhandling.EventBus;
import com.eshop.shared.rest.error.BadRequestException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.util.CollectionUtils.isEmpty;

@AllArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {
  private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

  private final BasketRepository basketRepository;
  private final IdentityService identityService;
  private final EventBus orderCheckoutsEventBus;

  @Override
  public Optional<CustomerBasket> findById(UUID basketId) {
    return basketRepository.findById(basketId);
  }

  @Override
  public CustomerBasket findByCustomerId(String customerId) {
    return getCustomerBasket(customerId);
  }

  @Override
  public CustomerBasket updateBasket(CustomerBasket updatedBasket) {
    var basketToUpdate = basketRepository.findByCustomerId(updatedBasket.getBuyerId())
      .map(b -> updatedBasket)
      .orElseGet(() -> new CustomerBasket(updatedBasket.getBuyerId()));

    return basketRepository.updateBasket(basketToUpdate);
  }

  @Transactional
  @Override
  public void checkout(BasketCheckout basketCheckout) {
    var userName = identityService.getUserName();
    var basket = getCustomerBasket(userName);

    if (isEmpty(basket.getItems())) {
      throw new BadRequestException("The basket is empty");
    }

    logger.info("Checking out the basket for user: {} - request id: {}", userName, basketCheckout.getRequestId());

    var event = new UserCheckoutAcceptedIntegrationEvent(
      userName,
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
      basketCheckout.getCardType(),
      basketCheckout.getBuyer(),
      basketCheckout.getRequestId(),
      basket
    );

    basket.changeStatusTo(BasketStatus.CheckedOut);
    basketRepository.updateBasket(basket);

    // Once basket is checkout, sends an integration event to order-processor to convert basket to order and proceeds
    // with order creation process.
    orderCheckoutsEventBus.publish(event);
  }

  @Override
  public void delete(UUID basketId) {
    basketRepository.delete(basketId);
  }

  private CustomerBasket getCustomerBasket(String customerId) {
    return basketRepository.findByCustomerId(customerId)
      .orElseGet(() -> basketRepository.updateBasket(new CustomerBasket(customerId)));
  }
}
