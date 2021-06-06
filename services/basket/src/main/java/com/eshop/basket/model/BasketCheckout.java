package com.eshop.basket.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class BasketCheckout {
  @NotEmpty
  private String city;
  @NotEmpty
  private String street;
  @NotEmpty
  private String state;
  @NotEmpty
  private String country;
  @NotEmpty
  private String zipCode;
  @NotEmpty
  private String cardNumber;
  @NotEmpty
  private String cardHolderName;
  @NotNull
  private LocalDate cardExpiration;
  @NotEmpty
  private String cardSecurityNumber;
  @NotNull
  private Integer cardTypeId;
  @NotEmpty
  private String buyer;
  @Setter
  private UUID requestId;
}
