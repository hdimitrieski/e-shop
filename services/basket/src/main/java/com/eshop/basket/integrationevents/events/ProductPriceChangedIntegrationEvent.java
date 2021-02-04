package com.eshop.basket.integrationevents.events;

import com.eshop.basket.shared.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

// An Integration Event is an event that can cause side effects to other microsrvices, Bounded-Contexts or external systems.
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductPriceChangedIntegrationEvent extends IntegrationEvent {
    private Long productId;
    private BigDecimal newPrice;
    private BigDecimal oldPrice;
}
