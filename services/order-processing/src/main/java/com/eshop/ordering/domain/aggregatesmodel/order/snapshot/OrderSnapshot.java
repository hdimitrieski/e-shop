package com.eshop.ordering.domain.aggregatesmodel.order.snapshot;

import com.eshop.ordering.domain.base.Snapshot;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class OrderSnapshot implements Snapshot {
  private final String id;
  private final LocalDateTime orderDate;
  private final String description;
  private final boolean draft;
  private final String orderStatus;
  private final String buyerId;
  private final String paymentMethodId;
  private final List<OrderItemSnapshot> orderItems;
  private final String street;
  private final String city;
  private final String state;
  private final String country;
  private final String zipCode;
}
