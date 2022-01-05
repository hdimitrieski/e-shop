package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketCheckoutDto {
  @JsonProperty
  private String city;
  @JsonProperty
  private String street;
  @JsonProperty
  private String state;
  @JsonProperty
  private String country;
  @JsonProperty
  private String zipCode;
  @JsonProperty
  private String cardNumber;
  @JsonProperty
  private String cardHolderName;
  @JsonProperty
  private LocalDate cardExpiration;
  @JsonProperty
  private String cardSecurityNumber;
  @JsonProperty
  private String cardType;
  @JsonProperty
  private String buyer;
}
