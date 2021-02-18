package com.eshop.ordering.api.application.commands;

import com.eshop.ordering.api.application.dtos.OrderItemDTO;
import com.eshop.ordering.api.application.dtos.ToOrderItemDTOMapper;
import com.eshop.ordering.api.application.models.BasketItem;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateOrderCommand {
    private final List<OrderItemDTO> orderItems;
    private final String userId;
    private final String userName;
    private final String city;
    private final String street;
    private final String state;
    private final String country;
    private final String zipCode;
    private final String cardNumber;
    private final String cardHolderName;
    private final LocalDateTime cardExpiration;
    private final String cardSecurityNumber;
    private final Integer cardTypeId;

    public CreateOrderCommand(List<BasketItem> basketItems, String userId, String userName, String city, String street,
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
