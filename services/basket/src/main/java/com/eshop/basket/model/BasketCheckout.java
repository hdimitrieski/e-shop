package com.eshop.basket.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class BasketCheckout {
  private String city;
  private String street;
  private String state;
  private String country;
  private String zipCode;
  private String cardNumber;
  private String cardHolderName;
  private LocalDate cardExpiration;
  private String cardSecurityNumber;
  private Integer cardTypeId;
  private String buyer;
  @Setter
  private UUID requestId;
}
