package com.eshop.catalog.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class OrderStockRejectedIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private List<ConfirmedOrderStockItem> orderStockItems;
}
