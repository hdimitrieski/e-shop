package com.eshop.gqlgateway.api.converters;

import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.models.CatalogItemDto;
import com.eshop.gqlgateway.types.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.eshop.gqlgateway.api.util.IdUtils.generateId;

@RequiredArgsConstructor
@Component
public class ToProductConverter {
  private final ToCategoryConverter toCategoryConverter;
  private final ToBrandConverter toBrandConverter;

  public Product convert(CatalogItemDto catalogItemDto) {
    return Product.newBuilder()
      .id(generateId(NodeType.Product, catalogItemDto.id()))
      .image(catalogItemDto.pictureFileName())
      .name(catalogItemDto.name())
      .description(catalogItemDto.description())
      .availableQuantity(catalogItemDto.availableStock())
      .category(toCategoryConverter.convert(catalogItemDto.category()))
      .brand(toBrandConverter.convert(catalogItemDto.brand()))
      .price(catalogItemDto.price())
      .build();
  }

}
