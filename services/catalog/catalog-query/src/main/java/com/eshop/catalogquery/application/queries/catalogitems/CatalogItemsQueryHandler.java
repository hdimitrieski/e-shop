package com.eshop.catalogquery.application.queries.catalogitems;

import com.eshop.catalogquery.application.querybus.QueryHandler;
import com.eshop.catalogquery.model.*;
import com.eshop.shared.rest.error.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.eshop.catalogquery.application.queries.catalogitems.CatalogSpecification.itemBrandEqual;
import static com.eshop.catalogquery.application.queries.catalogitems.CatalogSpecification.itemCategoryEqual;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Component
public class CatalogItemsQueryHandler implements QueryHandler<Page<CatalogItem>, CatalogItemsQuery> {

  private final CatalogItemRepository catalogItemRepository;
  private final BrandRepository brandRepository;
  private final CategoryRepository categoryRepository;

  @Override
  public Page<CatalogItem> handle(CatalogItemsQuery query) {
    final var brand = findBrand(query.brandId());
    final var category = findCategory(query.categoryId());
    final var page = PageRequest.of(query.pageIndex(), query.pageSize());

    return catalogItemRepository.findAll(
        Specification.where(itemBrandEqual(brand).and(itemCategoryEqual(category))),
        page.withSort(Sort.sort(CatalogItem.class).by(CatalogItem::getId).descending())
    );
  }

  private Brand findBrand(UUID id) {
    return nonNull(id)
        ? brandRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Catalog item brand with id: %d does not exist".formatted(id)))
        : null;
  }

  private Category findCategory(UUID id) {
    return nonNull(id)
        ? categoryRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Catalog item category with id: %d does not exist".formatted(id)))
        : null;
  }

}
