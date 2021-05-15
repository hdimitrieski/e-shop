package com.eshop.catalog.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductPriceChangedIntegrationEvent extends IntegrationEvent {
  private Long productId;
  private BigDecimal newPrice;
  private BigDecimal oldPrice;
}
