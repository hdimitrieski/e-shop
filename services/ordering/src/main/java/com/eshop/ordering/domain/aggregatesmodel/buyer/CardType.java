package com.eshop.ordering.domain.aggregatesmodel.buyer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CardType {
    Amex(1, "Amex"),
    Visa(2, "Visa"),
    MasterCard(3, "MasterCard");

    @Getter
    private final Integer id;

    @Getter
    private final String name;
}
