package com.eshop.catalog.controller;

import com.eshop.catalog.model.CatalogBrand;
import com.eshop.catalog.model.CatalogItem;
import com.eshop.catalog.model.CatalogType;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.nonNull;

final class CatalogSpecification {

  public static Specification<CatalogItem> catalogBrandEqual(CatalogBrand brand) {
    return (root, query, builder) -> nonNull(brand)
        ? builder.equal(root.get("catalogBrand"), brand)
        : builder.conjunction();
  }

  public static Specification<CatalogItem> catalogTypeEqual(CatalogType type) {
    return (root, query, builder) -> nonNull(type)
        ? builder.equal(root.get("catalogType"), type)
        : builder.conjunction();
  }

}
