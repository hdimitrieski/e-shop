package com.eshop.analytics.streamprocessing.events;

import com.eshop.analytics.model.OrderItem;
import com.eshop.shared.eventhandling.IntegrationEvent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToSubmittedIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private String orderStatus;
  private String buyerName;
  private Double totalPrice;
  private List<OrderItem> orderItems;

}
