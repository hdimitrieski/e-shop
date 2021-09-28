package com.eshop.catalog.domain.catalogitem;

import com.eshop.catalog.domain.base.ValueObject;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class Units extends ValueObject {

  private final Integer value;

  private Units(@NonNull Integer units) {
    Objects.requireNonNull(units, "Units cannot be null");

    if (units < 0) {
      throw new IllegalArgumentException("Units cannot be negative");
    }

    this.value = units;
  }

  public static Units of(@NonNull Integer value) {
    return new Units(value);
  }

  public static Units single() {
    return new Units(1);
  }

  public static Units empty() {
    return new Units(0);
  }

  public Units add(@NonNull Units unitsToAdd) {
    Objects.requireNonNull(unitsToAdd, "Units cannot be null");
    return Units.of(value + unitsToAdd.getValue());
  }

  public Units subtract(@NonNull Units unitsToSubtract) {
    Objects.requireNonNull(unitsToSubtract, "Units cannot be null");
    return Units.of(value - unitsToSubtract.getValue());
  }

  public boolean greaterThan(Units units) {
    return value > units.getValue();
  }

  public boolean isEmpty() {
    return value == 0;
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(value);
  }
}
