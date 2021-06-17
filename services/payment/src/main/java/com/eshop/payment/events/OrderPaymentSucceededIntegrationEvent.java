package com.eshop.payment.events;

import com.eshop.payment.model.PaymentStatus;
import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderPaymentSucceededIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private final PaymentStatus status = PaymentStatus.SUCCESS;
}
