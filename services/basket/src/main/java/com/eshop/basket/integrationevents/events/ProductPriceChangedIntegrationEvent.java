package com.eshop.basket.integrationevents.events;

import com.eshop.eventbus.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// An Integration Event is an event that can cause side effects to other microsrvices, Bounded-Contexts or external systems.
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductPriceChangedIntegrationEvent extends IntegrationEvent {
  private Long productId;
  private Double newPrice;
  private Double oldPrice;
}
