package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.base.Identifier;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.UUID;

@ToString
public class BuyerId extends Identifier {
  private BuyerId(UUID value) {
    super(value);
  }

  public static BuyerId of(@NonNull UUID value) {
    return new BuyerId(value);
  }

  public static BuyerId of(@NonNull String value) {
    return new BuyerId(UUID.fromString(value));
  }

}
