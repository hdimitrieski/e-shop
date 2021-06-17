package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.base.Identifier;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.UUID;

@ToString
public class OrderItemId extends Identifier {
  private OrderItemId(UUID value) {
    super(value);
  }

  public static OrderItemId of(@NonNull UUID value) {
    return new OrderItemId(value);
  }

  public static OrderItemId of(@NonNull String value) {
    return new OrderItemId(UUID.fromString(value));
  }

}
