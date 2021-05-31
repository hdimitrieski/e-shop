package com.eshop.payment.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderPaymentSucceededIntegrationEvent extends IntegrationEvent {
  private Integer orderId;
  private final String status = "successful";
}
