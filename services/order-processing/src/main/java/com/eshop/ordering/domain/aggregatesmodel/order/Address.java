package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.seedwork.ValueObject;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address extends ValueObject {
  @Getter
  @Column(name = "street")
  public String street;

  @Getter
  @Column(name = "city")
  public String city;

  @Getter
  @Column(name = "state")
  public String state;

  @Getter
  @Column(name = "country")
  public String country;

  @Getter
  @Column(name = "zip_code")
  public String zipCode;

  @Transient
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
