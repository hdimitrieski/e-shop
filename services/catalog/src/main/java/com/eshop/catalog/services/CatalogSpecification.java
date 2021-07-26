package com.eshop.catalog.services;

import com.eshop.catalog.model.Brand;
import com.eshop.catalog.model.CatalogItem;
import com.eshop.catalog.model.Category;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.nonNull;

final class CatalogSpecification {

  public static Specification<CatalogItem> itemBrandEqual(Brand brand) {
    return (root, query, builder) -> nonNull(brand)
        ? builder.equal(root.get("brand"), brand)
        : builder.conjunction();
  }

  public static Specification<CatalogItem> itemCategoryEqual(Category category) {
    return (root, query, builder) -> nonNull(category)
        ? builder.equal(root.get("category"), category)
        : builder.conjunction();
  }

}
