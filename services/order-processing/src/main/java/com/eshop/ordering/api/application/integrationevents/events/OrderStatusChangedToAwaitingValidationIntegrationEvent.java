package com.eshop.ordering.api.application.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import com.eshop.ordering.api.application.integrationevents.events.models.OrderStockItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToAwaitingValidationIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private String orderStatus;
  private String buyerName;
  private List<OrderStockItem> orderStockItems;
}
