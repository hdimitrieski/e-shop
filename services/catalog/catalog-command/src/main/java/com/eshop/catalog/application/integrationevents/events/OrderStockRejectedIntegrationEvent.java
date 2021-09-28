package com.eshop.catalog.application.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderStockRejectedIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private List<ConfirmedOrderStockItem> orderStockItems;
}
