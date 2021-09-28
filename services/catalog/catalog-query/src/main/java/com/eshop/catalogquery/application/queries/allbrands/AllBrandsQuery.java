package com.eshop.catalogquery.application.queries.allbrands;

import com.eshop.catalogquery.application.querybus.Query;
import com.eshop.catalogquery.model.Brand;

public record AllBrandsQuery() implements Query<Iterable<Brand>> {
}
