package com.eshop.ordering.api.application.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToSubmittedIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private String orderStatus;
  private String buyerName;
  private Double totalPrice;
  private List<OrderItemDto> orderItems;

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  public static class OrderItemDto {
    private String id;
    private String name;
    private Double unitPrice;
    private Integer units;
  }
}
