package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.base.ValueObject;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@ToString
public class BuyerName extends ValueObject {
  private final String name;

  private BuyerName(@NonNull String name) {
    if (isEmpty(name)) {
      throw new IllegalArgumentException("Name cannot be empty");
    }

    this.name = name;
  }

  public static BuyerName of(String name) {
    return new BuyerName(name);
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(name);
  }
}
