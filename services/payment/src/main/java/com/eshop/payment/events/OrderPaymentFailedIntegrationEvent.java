package com.eshop.payment.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderPaymentFailedIntegrationEvent extends IntegrationEvent {
  private Integer orderId;
}
