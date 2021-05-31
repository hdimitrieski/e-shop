package com.eshop.ordering.api.application.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToStockConfirmedIntegrationEvent extends IntegrationEvent {
  private Long orderId;
  private String orderStatus;
  private String buyerName;
}
