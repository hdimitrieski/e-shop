package com.eshop.analytics.streamprocessing.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToPaidIntegrationEvent {
  private String orderId;
}
