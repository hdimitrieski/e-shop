package com.eshop.catalogquery.application.queries.catalogitemswithname;

import com.eshop.catalogquery.application.querybus.QueryHandler;
import com.eshop.catalogquery.model.CatalogItem;
import com.eshop.catalogquery.model.CatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CatalogItemWithNameQueryHandler implements QueryHandler<Page<CatalogItem>, CatalogItemWithNameQuery> {

  private final CatalogItemRepository catalogItemRepository;

  @Override
  public Page<CatalogItem> handle(CatalogItemWithNameQuery query) {
    final var page = PageRequest.of(query.pageIndex(), query.pageSize());
    return catalogItemRepository.findAllByName(query.name(), page);
  }
}
