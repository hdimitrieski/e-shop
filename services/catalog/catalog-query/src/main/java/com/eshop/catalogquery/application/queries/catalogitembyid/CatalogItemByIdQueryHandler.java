package com.eshop.catalogquery.application.queries.catalogitembyid;

import com.eshop.catalogquery.application.querybus.QueryHandler;
import com.eshop.catalogquery.model.CatalogItem;
import com.eshop.catalogquery.model.CatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CatalogItemByIdQueryHandler implements QueryHandler<Optional<CatalogItem>, CatalogItemByIdQuery> {

  private final CatalogItemRepository catalogItemRepository;

  @Override
  public Optional<CatalogItem> handle(CatalogItemByIdQuery query) {
    return catalogItemRepository.findById(query.catalogItemId());
  }
}
