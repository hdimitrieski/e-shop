package com.eshop.ordering.domain.aggregatesmodel.order.snapshot;

import com.eshop.ordering.domain.base.Snapshot;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class OrderItemSnapshot implements Snapshot {
  private final String id;
  private final String productName;
  private final Double discount;
  private final String pictureUrl;
  private final Double unitPrice;
  private final Integer units;
  private final UUID productId;
}
