package com.eshop.catalogquery.api;

import com.eshop.catalogquery.application.queries.allbrands.AllBrandsQuery;
import com.eshop.catalogquery.application.queries.allcategories.AllCategoryQuery;
import com.eshop.catalogquery.application.queries.brandbyid.BrandByIdQuery;
import com.eshop.catalogquery.application.queries.catalogitembyid.CatalogItemByIdQuery;
import com.eshop.catalogquery.application.queries.catalogitems.CatalogItemsQuery;
import com.eshop.catalogquery.application.queries.catalogitemsbyids.CatalogItemsByIdsQuery;
import com.eshop.catalogquery.application.queries.catalogitemswithname.CatalogItemWithNameQuery;
import com.eshop.catalogquery.application.queries.categorybyid.CategoryByIdQuery;
import com.eshop.catalogquery.application.querybus.QueryBus;
import com.eshop.catalogquery.model.Brand;
import com.eshop.catalogquery.model.CatalogItem;
import com.eshop.catalogquery.model.Category;
import com.eshop.shared.rest.error.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isEmpty;

/**
 * Handle requests for catalog service.
 */
@RequestMapping("catalog")
@RestController
@RequiredArgsConstructor
public class CatalogController {
  private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

  private final QueryBus queryBus;

  /**
   * Returns catalog items for given item ids.
   *
   * @param ids catalog item ids
   * @return catalog items
   */
  @RequestMapping("items/withids/{ids}")
  public Iterable<CatalogItem> catalogItemsByIds(@PathVariable String ids) {
    if (isEmpty(ids)) {
      throw new BadRequestException("Invalid ids value");
    }

    final var itemIds = Arrays.
      stream(ids.split(","))
      .map(UUID::fromString)
      .collect(Collectors.toSet());
    return queryBus.execute(new CatalogItemsByIdsQuery(itemIds));
  }

  /**
   * Returns catalog items that belong to given brand and type in the given page.
   *
   * @param pageSize   number of items
   * @param pageIndex  page
   * @param brandId    item brand
   * @param categoryId item category
   * @return catalog items
   */
  @RequestMapping("items")
  public Page<CatalogItem> catalogItems(
    @RequestParam(defaultValue = "10", required = false) Integer pageSize,
    @RequestParam(defaultValue = "0", required = false) Integer pageIndex,
    @RequestParam(required = false) UUID brandId,
    @RequestParam(required = false) UUID categoryId
  ) {
    logger.info("Find catalog items - page size: {}, page index: {}, brand: {}, type: {}", pageSize, pageIndex, brandId, categoryId);

    return queryBus.execute(new CatalogItemsQuery(pageSize, pageIndex, brandId, categoryId));
  }

  /**
   * Returns catalog item by given id.
   *
   * @param id item id
   * @return catalog item
   */
  @RequestMapping("items/{id}")
  public ResponseEntity<CatalogItem> catalogItem(@PathVariable UUID id) {
    logger.info("Find catalog item: {}", id);
    return ResponseEntity.of(queryBus.execute(new CatalogItemByIdQuery(id)));
  }

  /**
   * Returns catalog items by given name in give page.
   *
   * @param pageSize  number of items to be returned
   * @param pageIndex page
   * @param name      name of item
   * @return catalog items
   */
  @RequestMapping("items/withname/{name}")
  public Page<CatalogItem> catalogItems(
    @RequestParam(defaultValue = "10", required = false) Integer pageSize,
    @RequestParam(defaultValue = "0", required = false) Integer pageIndex,
    @PathVariable String name
  ) {
    if (isEmpty(name)) {
      throw new BadRequestException("The name must be at least one character long");
    }
    return queryBus.execute(new CatalogItemWithNameQuery(pageSize, pageIndex, name));
  }

  /**
   * Returns catalog brand by id.
   *
   * @param id brand id
   * @return brand
   */
  @RequestMapping("brands/{id}")
  public Optional<Brand> brand(@PathVariable UUID id) {
    return queryBus.execute(new BrandByIdQuery(id));
  }

  /**
   * Returns all catalog brands.
   *
   * @return all catalog brands
   */
  @RequestMapping("brands")
  public Iterable<Brand> catalogBrands() {
    return queryBus.execute(new AllBrandsQuery());
  }

  /**
   * Returns all catalog brands.
   *
   * @return all catalog brands
   */
  @RequestMapping("categories")
  public Iterable<Category> catalogCategories() {
    return queryBus.execute(new AllCategoryQuery());
  }

  /**
   * Returns category by id.
   *
   * @param id category id
   * @return category
   */
  @RequestMapping("categories/{id}")
  public Optional<Category> category(@PathVariable UUID id) {
    return queryBus.execute(new CategoryByIdQuery(id));
  }

}

