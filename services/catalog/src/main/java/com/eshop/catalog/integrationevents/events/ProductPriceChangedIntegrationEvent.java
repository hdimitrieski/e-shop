package com.eshop.catalog.integrationevents.events;

import com.eshop.catalog.shared.IntegrationEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class ProductPriceChangedIntegrationEvent extends IntegrationEvent {
    private final Long productId;
    private final BigDecimal newPrice;
    private final BigDecimal oldPrice;
}
