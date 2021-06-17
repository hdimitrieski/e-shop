package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.base.Identifier;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.UUID;

@ToString
public class OrderId extends Identifier {
  private OrderId(UUID uuid) {
    super(uuid);
  }

  public static OrderId of(@NonNull UUID value) {
    return new OrderId(value);
  }

  public static OrderId of(@NonNull String value) {
    return new OrderId(UUID.fromString(value));
  }

}
