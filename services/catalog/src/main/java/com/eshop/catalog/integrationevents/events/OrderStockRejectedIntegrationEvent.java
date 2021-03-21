package com.eshop.catalog.integrationevents.events;

import com.eshop.eventbus.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class OrderStockRejectedIntegrationEvent extends IntegrationEvent {
  private Long orderId;
  private List<ConfirmedOrderStockItem> orderStockItems;
}
