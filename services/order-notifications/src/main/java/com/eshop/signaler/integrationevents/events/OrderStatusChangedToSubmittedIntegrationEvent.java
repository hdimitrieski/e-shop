package com.eshop.signaler.integrationevents.events;

import com.eshop.eventbus.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToSubmittedIntegrationEvent extends IntegrationEvent {
  private Long orderId;
  private String orderStatus;
  private String buyerName;
}
