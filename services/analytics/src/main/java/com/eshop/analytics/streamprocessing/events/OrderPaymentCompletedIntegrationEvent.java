package com.eshop.analytics.streamprocessing.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderPaymentCompletedIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private String status;
}
