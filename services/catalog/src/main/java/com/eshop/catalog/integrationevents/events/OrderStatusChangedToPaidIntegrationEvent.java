package com.eshop.catalog.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToPaidIntegrationEvent extends IntegrationEvent {
  private Long orderId;
  // TODO HD is this ok ?
  private String orderStatus;
  private String buyerName;
  private List<OrderStockItem> orderStockItems;
}
