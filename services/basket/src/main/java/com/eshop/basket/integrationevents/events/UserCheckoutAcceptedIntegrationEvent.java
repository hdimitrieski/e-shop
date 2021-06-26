package com.eshop.basket.integrationevents.events;

import com.eshop.basket.model.CustomerBasket;
import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCheckoutAcceptedIntegrationEvent extends IntegrationEvent {
  private String userId;
  private String userName;
  private String city;
  private String street;
  private String state;
  private String country;
  private String zipCode;
  private String cardNumber;
  private String cardHolderName;
  private LocalDate cardExpiration;
  private String cardSecurityNumber;
  private String cardType;
  private String buyer;
  private UUID requestId;
  private CustomerBasket basket;
}
