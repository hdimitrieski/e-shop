package com.eshop.catalog.integrationevents.events;

import com.eshop.catalog.shared.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderStockConfirmedIntegrationEvent extends IntegrationEvent {
    private final Long orderId;
}
