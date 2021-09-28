package com.eshop.catalogquery.application.queries.catalogitemsbyids;

import com.eshop.catalogquery.application.querybus.Query;
import com.eshop.catalogquery.model.CatalogItem;

import java.util.Set;
import java.util.UUID;

public record CatalogItemsByIdsQuery(
    Set<UUID> catalogItemIds
) implements Query<Iterable<CatalogItem>> {
}
