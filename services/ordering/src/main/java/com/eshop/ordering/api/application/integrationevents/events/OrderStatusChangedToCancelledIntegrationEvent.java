package com.eshop.ordering.api.application.integrationevents.events;

import com.eshop.ordering.api.application.integrationevents.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToCancelledIntegrationEvent extends IntegrationEvent {
  private Long orderId;
  private String orderStatus;
  private String buyerName;
}
