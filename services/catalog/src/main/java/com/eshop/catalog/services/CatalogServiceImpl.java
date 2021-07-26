package com.eshop.catalog.services;

import com.eshop.catalog.integrationevents.IntegrationEventService;
import com.eshop.catalog.integrationevents.events.ProductPriceChangedIntegrationEvent;
import com.eshop.catalog.model.*;
import com.eshop.shared.rest.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.eshop.catalog.services.CatalogSpecification.itemBrandEqual;
import static com.eshop.catalog.services.CatalogSpecification.itemCategoryEqual;

@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService {

    private static final Logger logger = LoggerFactory.getLogger(CatalogService.class);

    private final CatalogItemRepository catalogItemRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final IntegrationEventService integrationEventService;

    @Value("${spring.kafka.consumer.topic.productPriceChanges}")
    private String productPriceChangesTopic;

    @Override
    public Optional<CatalogItem> getItemById(Long id) {
        return catalogItemRepository.findById(id);
    }

    @Override
    public Iterable<CatalogItem> getItemsByIds(List<Long> itemIds) {
        return catalogItemRepository.findAllById(itemIds);
    }

    @Override
    public Page<CatalogItem> getItems(Brand brand, Category category, PageRequest page) {
        logger.info("Load items");
        return catalogItemRepository.findAll(
                Specification.where(itemBrandEqual(brand).and(itemCategoryEqual(category))),
                page
        );
    }

    @Override
    public Page<CatalogItem> getItems(String name, PageRequest page) {
        return catalogItemRepository.findAllByName(name, page);
    }

    @Override
    public void updateItem(CatalogItem item) {
        catalogItemRepository.findById(item.getId())
                .ifPresentOrElse(
                        itemFromDb -> updateCatalogItem(item, itemFromDb),
                        () -> {
                            throw new NotFoundException("Item with id: %d not found.".formatted(item.getId()));
                        }
                );
    }

    @Override
    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Iterable<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    private void updateCatalogItem(CatalogItem item, CatalogItem itemFromDb) {
        final var oldPrice = itemFromDb.getPrice();
        final var updatedItem = item.toBuilder().build();

        if (priceChanged(oldPrice, item.getPrice())) {
            // Create Integration Event to be published through the Event Bus
            var priceChangedEvent = new ProductPriceChangedIntegrationEvent(
                    itemFromDb.getId(),
                    updatedItem.getPrice(),
                    oldPrice
            );

            integrationEventService.saveEventAndCatalogContextChanges(productPriceChangesTopic, priceChangedEvent);
        }
        catalogItemRepository.save(updatedItem);
    }

    private boolean priceChanged(BigDecimal oldPrice, BigDecimal newPrice) {
        return oldPrice.compareTo(newPrice) != 0;
    }

}
