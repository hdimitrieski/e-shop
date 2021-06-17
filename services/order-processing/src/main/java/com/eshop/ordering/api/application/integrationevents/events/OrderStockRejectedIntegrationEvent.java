package com.eshop.ordering.api.application.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import com.eshop.ordering.api.application.integrationevents.events.models.ConfirmedOrderStockItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStockRejectedIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private List<ConfirmedOrderStockItem> orderStockItems;
}
