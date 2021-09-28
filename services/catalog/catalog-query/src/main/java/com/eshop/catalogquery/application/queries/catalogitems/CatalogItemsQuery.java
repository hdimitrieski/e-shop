package com.eshop.catalogquery.application.queries.catalogitems;

import com.eshop.catalogquery.application.querybus.Query;
import com.eshop.catalogquery.model.CatalogItem;
import org.springframework.data.domain.Page;

import java.util.UUID;

public record CatalogItemsQuery(
    Integer pageSize,
    Integer pageIndex,
    UUID brandId,
    UUID categoryId
) implements Query<Page<CatalogItem>> {
}
