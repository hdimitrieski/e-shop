package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.base.ValueObject;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@ToString
public class UserId extends ValueObject {
  @Getter
  private final String id;

  private UserId(@NonNull String id) {
    if (isEmpty(id)) {
      throw new IllegalArgumentException("User id cannot be empty");
    }

    this.id = id;
  }

  public static UserId of(String id) {
    return new UserId(id);
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(id);
  }
}
