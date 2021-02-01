package com.eshop.catalog.integrationevents.events;

import com.eshop.catalog.shared.IntegrationEvent;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderStockRejectedIntegrationEvent extends IntegrationEvent {
    private final Long orderId;
    private final List<ConfirmedOrderStockItem> orderStockItems;
}
