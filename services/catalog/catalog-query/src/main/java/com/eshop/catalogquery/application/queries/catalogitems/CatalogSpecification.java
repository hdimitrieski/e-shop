package com.eshop.catalogquery.application.queries.catalogitems;

import com.eshop.catalogquery.model.Brand;
import com.eshop.catalogquery.model.CatalogItem;
import com.eshop.catalogquery.model.Category;
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
