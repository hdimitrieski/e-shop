package com.eshop.catalog.controller;

import com.eshop.catalog.integrationevents.IntegrationEventService;
import com.eshop.catalog.integrationevents.events.ProductPriceChangedIntegrationEvent;
import com.eshop.catalog.model.*;
import com.eshop.error.BadRequestException;
import com.eshop.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@RequestMapping("catalog")
@RestController
@RequiredArgsConstructor
public class CatalogController {
  private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

  private final CatalogItemRepository catalogItemRepository;
  private final CatalogTypeRepository catalogTypeRepository;
  private final CatalogBrandRepository catalogBrandRepository;
  private final IntegrationEventService integrationEventService;
  @Value("${spring.kafka.consumer.topic.productPriceChanges}")
  private String productPriceChangesTopic;

  @RequestMapping("items/withids/{ids}")
  public Iterable<CatalogItem> catalogItemsByIds(@PathVariable String ids) {
    if (isEmpty(ids)) {
      throw new BadRequestException("Invalid ids value");
    }

    return catalogItemRepository.findAllById(Arrays.
        stream(ids.split(","))
        .map(Long::valueOf)
        .collect(Collectors.toList())
    );
  }

  @RequestMapping("items")
  public Page<CatalogItem> catalogItems(
      @RequestParam(defaultValue = "10", required = false) Integer pageSize,
      @RequestParam(defaultValue = "0", required = false) Integer pageIndex,
      @AuthenticationPrincipal Jwt jwt
  ) {
    logger.info("Find catalog items - page size: {}, page index: {}", pageSize, pageIndex);
    logger.info("Resource accessed by: {} (with subjectId: {})", jwt.getClaims().get("user_name"), jwt.getSubject());
    return catalogItemRepository.findAll(PageRequest.of(pageIndex, pageSize));
  }

  @RequestMapping("items/{id}")
  public ResponseEntity<CatalogItem> catalogItems(@PathVariable Long id) {
    logger.info("Find catalog item: {}", id);
    return ResponseEntity.of(catalogItemRepository.findById(id));
  }

  @RequestMapping("items/withname/{name}")
  public Page<CatalogItem> catalogItems(
      @RequestParam(defaultValue = "10", required = false) Integer pageSize,
      @RequestParam(defaultValue = "0", required = false) Integer pageIndex,
      @PathVariable String name // TODO length should be min 1 character
  ) {
    return null; //return catalogItemRepository.findAllByName(name);
  }

  @RequestMapping("catalogtypes")
  public Iterable<CatalogType> findAllCatalogTypes() {
    logger.info("Find all catalog types");
    return catalogTypeRepository.findAll();
  }

  @RequestMapping("catalogbrands")
  public Iterable<CatalogBrand> findAllCatalogBrands() {
    logger.info("Find all catalog brands");
    return catalogBrandRepository.findAll();
  }

  @RequestMapping(method = RequestMethod.PUT, path = "items")
  @Transactional
  public void updateProduct(@RequestBody CatalogItem productToUpdate) {
    logger.info("Update product: {}", productToUpdate.getId());
    catalogItemRepository.findById(productToUpdate.getId())
        .ifPresentOrElse(catalogItem -> {
          var oldPrice = catalogItem.getPrice();
          // TODO HD it doesn't work
          var raiseProductPriceChangedEvent = oldPrice.compareTo(productToUpdate.getPrice()) != 0;

          // entityManager.getTransaction().begin();
          catalogItem = productToUpdate;
          // entityManager.persist(catalogItem);

          if (raiseProductPriceChangedEvent) {
            //Create Integration Event to be published through the Event Bus
            var priceChangedEvent = new ProductPriceChangedIntegrationEvent(
                catalogItem.getId(),
                productToUpdate.getPrice(),
                oldPrice
            );

            // Achieving atomicity between original Catalog database operation and the IntegrationEventLog thanks to a local transaction
            integrationEventService.saveEventAndCatalogContextChanges(productPriceChangesTopic, priceChangedEvent);

            // Publish through the Event Bus and mark the saved event as published
            integrationEventService.publishThroughEventBus(productPriceChangesTopic, priceChangedEvent);
          } else {
            // entityManager.getTransaction().commit();
          }
          catalogItemRepository.save(catalogItem);
        }, () -> {
          throw new NotFoundException(String.format("Item with id %d not found.", productToUpdate.getId()));
        });

  }

  @RequestMapping(method = RequestMethod.POST, path = "items")
  public void createProduct(@RequestBody CatalogItem product) {

  }

  @RequestMapping(method = RequestMethod.DELETE, path = "items/{id}")
  public void deleteProduct(@PathVariable Long id) {

  }
}
