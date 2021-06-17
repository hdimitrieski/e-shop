package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.base.ValueObject;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Builder
@ToString
public class Address extends ValueObject {
  private final String street;
  private final String city;
  private final String state;
  private final String country;
  private final String zipCode;

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(
        street,
        city,
        state,
        country,
        zipCode
    );
  }
}
