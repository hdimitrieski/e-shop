package com.eshop.catalog.domain.catalogitem;

import com.eshop.catalog.domain.base.ValueObject;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@ToString
public class Brand extends ValueObject {
  private final String name;
  private final UUID brandId;

  private Brand(@NotNull UUID brandId, @NonNull String name) {
    if (isEmpty(name)) {
      throw new IllegalArgumentException("Brand name cannot be empty");
    }
    if (isNull(brandId)) {
      throw new IllegalArgumentException("Brand id cannot be null");
    }

    this.name = name;
    this.brandId = brandId;
  }

  public static Brand of(UUID brandId, String name) {
    return new Brand(brandId, name);
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(name);
  }
}
