package com.eshop.gqlgateway.models;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class BasketCheckoutDto {
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
}
