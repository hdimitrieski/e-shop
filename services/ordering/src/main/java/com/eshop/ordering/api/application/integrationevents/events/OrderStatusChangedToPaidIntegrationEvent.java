package com.eshop.ordering.api.application.integrationevents.events;

import com.eshop.eventbus.IntegrationEvent;
import com.eshop.ordering.api.application.integrationevents.events.models.OrderStockItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToPaidIntegrationEvent extends IntegrationEvent {
    private Long orderId;
    private String buyerName;
    private String orderStatus;
    private List<OrderStockItem> orderStockItems;
}
