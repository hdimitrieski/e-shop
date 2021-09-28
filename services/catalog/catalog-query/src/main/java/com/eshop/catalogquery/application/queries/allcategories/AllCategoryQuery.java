package com.eshop.catalogquery.application.queries.allcategories;

import com.eshop.catalogquery.application.querybus.Query;
import com.eshop.catalogquery.model.Brand;
import com.eshop.catalogquery.model.Category;

public record AllCategoryQuery() implements Query<Iterable<Category>> {
}
