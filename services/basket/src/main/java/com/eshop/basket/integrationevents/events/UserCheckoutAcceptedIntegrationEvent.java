package com.eshop.basket.integrationevents.events;

import com.eshop.basket.model.CustomerBasket;
import com.eshop.basket.shared.IntegrationEvent;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class UserCheckoutAcceptedIntegrationEvent extends IntegrationEvent {
    private final String userId;
    private final String userName;
    private final String city;
    private final String street;
    private final String state;
    private final String country;
    private final String zipCode;
    private final String cardNumber;
    private final String cardHolderName;
    private final LocalDate cardExpiration;
    private final String cardSecurityNumber;
    private final Integer cardTypeId;
    private final String buyer;
    private final UUID requestId;
    private final CustomerBasket basket;
}
