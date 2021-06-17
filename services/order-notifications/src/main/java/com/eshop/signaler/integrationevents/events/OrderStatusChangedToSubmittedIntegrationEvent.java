package com.eshop.signaler.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToSubmittedIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private String orderStatus;
  private String buyerName;
}
