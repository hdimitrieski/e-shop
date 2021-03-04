package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.api.application.dtos.OrderItemDTO;
import com.eshop.ordering.api.application.dtos.ToOrderItemDTOMapper;
import com.eshop.ordering.api.application.models.BasketItem;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateOrderCommand implements Command<Boolean> {
  private final List<OrderItemDTO> orderItems;
  private final String userId;
  private final String userName;
  @NotBlank
  private final String city;
  @NotBlank
  private final String street;
  @NotBlank
  private final String state;
  @NotBlank
  private final String country;
  @NotBlank
  private final String zipCode;
  @NotBlank
  @Size(min = 5, max = 19)
  private final String cardNumber;
  @NotBlank
  private final String cardHolderName;
  // TODO HD @CardExpirationDate ...
  @NotNull
  private final LocalDateTime cardExpiration;
  @NotBlank
  @Size(min = 3)
  private final String cardSecurityNumber;
  @NotNull
  private final Integer cardTypeId;

  public CreateOrderCommand(
      List<BasketItem> basketItems, String userId, String userName, String city, String street,
      String state, String country, String zipCode, String cardNumber, String cardHolderName,
      LocalDateTime cardExpiration, String cardSecurityNumber, int cardTypeId) {
    orderItems = basketItems.stream()
        .map(basketItem -> new ToOrderItemDTOMapper().map(basketItem))
        .collect(Collectors.toList());
    this.userId = userId;
    this.userName = userName;
    this.city = city;
    this.street = street;
    this.state = state;
    this.country = country;
    this.zipCode = zipCode;
    this.cardNumber = cardNumber;
    this.cardHolderName = cardHolderName;
    this.cardExpiration = cardExpiration;
    this.cardSecurityNumber = cardSecurityNumber;
    this.cardTypeId = cardTypeId;
  }
}
