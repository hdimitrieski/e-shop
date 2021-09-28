package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.base.ValueObject;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class Product extends ValueObject {
  private final UUID productId;
  private final String productName;
  private final Price unitPrice;
  private final Price discount;
  private final String pictureUrl;
  @Builder.Default
  private final Units units = Units.single();

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(productId, productName, unitPrice, discount, pictureUrl, units);
  }
}
