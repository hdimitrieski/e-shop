package com.eshop.basket.model;

import com.eshop.validation.CardExpirationDate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
  @Size(min = 5, max = 19)
  private String cardNumber;
  @NotEmpty
  private String cardHolderName;
  @NotNull
  @CardExpirationDate
  private LocalDate cardExpiration;
  @NotEmpty
  @Size(min = 3)
  private String cardSecurityNumber;
  @NotNull
  private String cardType;
  @NotEmpty
  private String buyer;
  @Setter
  private UUID requestId;
}
