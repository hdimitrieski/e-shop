package com.eshop.basket.integrationevents.events;

import com.eshop.basket.shared.IntegrationEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

// An Integration Event is an event that can cause side effects to other microsrvices, Bounded-Contexts or external systems.
@RequiredArgsConstructor
@Getter
public class ProductPriceChangedIntegrationEvent extends IntegrationEvent {
    private final Long productId;
    private final BigDecimal newPrice;
    private final BigDecimal oldPrice;
}
