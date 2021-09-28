package com.eshop.catalogquery.application.projections;

import com.eshop.catalog.shared.events.StockRemoved;
import com.eshop.catalogquery.model.CatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@ProcessingGroup("kafka-group")
@Component
public class StockRemovedProjection {
  private static final Logger logger = LoggerFactory.getLogger(StockRemovedProjection.class);

  private final CatalogItemRepository catalogItemRepository;

  @EventHandler
  @Transactional
  public void project(StockRemoved event) {
    logger.info("Handling event: {} ({})", event.getId(), event.getClass().getSimpleName());

    final var catalogItem = catalogItemRepository.findById(event.getId())
        .orElseThrow(() -> new RuntimeException("Catalog item not found"));

    catalogItem.setAvailableStock(event.getAvailableStock());
    catalogItemRepository.save(catalogItem);
  }
}
