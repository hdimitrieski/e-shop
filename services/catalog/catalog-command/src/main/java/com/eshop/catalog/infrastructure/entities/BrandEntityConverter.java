package com.eshop.catalog.infrastructure.entities;

import com.eshop.catalog.domain.catalogitem.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandEntityConverter implements EntityConverter<BrandEntity, Brand> {

  @Override
  public BrandEntity toEntity(Brand brand) {
    return BrandEntity.builder()
        .brandId(brand.getBrandId())
        .name(brand.getName())
        .build();
  }

  @Override
  public Brand fromEntity(BrandEntity entity) {
    return Brand.of(entity.getBrandId(), entity.getName());
  }
}
