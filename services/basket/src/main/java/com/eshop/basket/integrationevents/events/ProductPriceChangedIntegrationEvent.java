package com.eshop.basket.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

// An Integration Event is an event that can cause side effects to other microsrvices, Bounded-Contexts or external systems.
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductPriceChangedIntegrationEvent extends IntegrationEvent {
  private UUID productId;
  private Double newPrice;
  private Double oldPrice;
}
