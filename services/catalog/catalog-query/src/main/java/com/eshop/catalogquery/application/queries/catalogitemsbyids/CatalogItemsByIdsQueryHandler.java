package com.eshop.catalogquery.application.queries.catalogitemsbyids;

import com.eshop.catalogquery.application.querybus.QueryHandler;
import com.eshop.catalogquery.model.CatalogItem;
import com.eshop.catalogquery.model.CatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CatalogItemsByIdsQueryHandler implements QueryHandler<Iterable<CatalogItem>, CatalogItemsByIdsQuery> {

  private final CatalogItemRepository catalogItemRepository;

  @Override
  public Iterable<CatalogItem> handle(CatalogItemsByIdsQuery query) {
    return catalogItemRepository.findAllById(query.catalogItemIds());
  }
}
