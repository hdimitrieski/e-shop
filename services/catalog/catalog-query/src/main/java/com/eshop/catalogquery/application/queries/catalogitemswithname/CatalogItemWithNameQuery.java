package com.eshop.catalogquery.application.queries.catalogitemswithname;

import com.eshop.catalogquery.application.querybus.Query;
import com.eshop.catalogquery.model.CatalogItem;
import org.springframework.data.domain.Page;

public record CatalogItemWithNameQuery(
    Integer pageSize,
    Integer pageIndex,
    String name
) implements Query<Page<CatalogItem>> {
}
