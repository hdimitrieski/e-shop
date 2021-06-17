package com.eshop.ordering.api.application.integrationevents.events;

import com.eshop.ordering.api.application.integrationevents.events.models.PaymentStatus;
import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderPaymentStatusChangedIntegrationEvent extends IntegrationEvent {
    private String orderId;
    private PaymentStatus status;
}
