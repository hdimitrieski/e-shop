package com.eshop.gqlgateway.api.converters;

import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.models.BrandDto;
import com.eshop.gqlgateway.types.Brand;
import org.springframework.stereotype.Component;

import static com.eshop.gqlgateway.api.util.IdUtils.generateId;

@Component
public class ToBrandConverter {

  public Brand convert(BrandDto brandDto) {
    return Brand.newBuilder()
      .id(generateId(NodeType.Brand, brandDto.id()))
      .name(brandDto.name())
      .build();
  }
}
