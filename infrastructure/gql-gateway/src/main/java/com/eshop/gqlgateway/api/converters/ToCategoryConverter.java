package com.eshop.gqlgateway.api.converters;

import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.models.CategoryDto;
import com.eshop.gqlgateway.types.Category;
import org.springframework.stereotype.Component;

import static com.eshop.gqlgateway.api.util.IdUtils.generateId;

@Component
public class ToCategoryConverter {

  public Category convert(CategoryDto categoryDto) {
    return Category.newBuilder()
      .id(generateId(NodeType.Category, categoryDto.id()))
      .name(categoryDto.name())
      .build();
  }
}
