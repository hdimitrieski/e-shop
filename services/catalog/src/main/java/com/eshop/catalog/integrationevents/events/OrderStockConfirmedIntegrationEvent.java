package com.eshop.catalog.integrationevents.events;

import com.eshop.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStockConfirmedIntegrationEvent extends IntegrationEvent {
  private Long orderId;
}
