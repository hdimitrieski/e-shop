package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.base.Identifier;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.UUID;

@ToString
public class PaymentMethodId extends Identifier {
  private PaymentMethodId(UUID value) {
    super(value);
  }

  public static PaymentMethodId of(@NonNull UUID value) {
    return new PaymentMethodId(value);
  }

  public static PaymentMethodId of(@NonNull String value) {
    return new PaymentMethodId(UUID.fromString(value));
  }

}
