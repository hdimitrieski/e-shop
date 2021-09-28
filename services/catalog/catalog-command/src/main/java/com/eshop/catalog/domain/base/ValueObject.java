package com.eshop.catalog.domain.base;

import java.util.List;
import java.util.Objects;

public abstract class ValueObject {

  protected abstract List<Object> getEqualityComponents();

  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var other = (ValueObject) obj;

    return this.getEqualityComponents().equals(other.getEqualityComponents());
  }

  @Override
  public int hashCode() {
    return getEqualityComponents().stream().filter(Objects::nonNull).map(Object::hashCode)
        .reduce((x, y) -> x ^ y)
        .orElse(0);
  }

}
