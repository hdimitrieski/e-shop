package com.eshop.ordering.api.application.integrationevents.events;

import com.eshop.eventbus.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStockConfirmedIntegrationEvent extends IntegrationEvent {
  private Long orderId;
}
