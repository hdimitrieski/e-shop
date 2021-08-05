package com.eshop.catalog.services;

import com.eshop.catalog.model.Brand;
import com.eshop.catalog.model.CatalogItem;
import com.eshop.catalog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * Catalog service.
 */
public interface CatalogService {

    /**
     * Returns item by id.
     *
     * @param id item id
     * @return catalog item
     */
    Optional<CatalogItem> getItemById(Long id);

    /**
     * Returns items by ids.
     *
     * @param itemIds item ids
     * @return catalog items
     */
    Iterable<CatalogItem> getItemsByIds(List<Long> itemIds);

    /**
     * Returns items by given brand and category in given page.
     *
     * @param brand       item brand
     * @param category    item category
     * @param pageRequest page
     * @return catalog items
     */
    Page<CatalogItem> getItems(Brand brand, Category category, PageRequest pageRequest);

    /**
     * Returns items by given name in given page.
     *
     * @param name item name
     * @param page page
     * @return catalog items
     */
    Page<CatalogItem> getItems(String name, PageRequest page);

    /**
     * Updates catalog item
     *
     * @param item item to be updated
     */
    void updateItem(CatalogItem item);

    /**
     * Add new catalog item
     *
     * @param item catalog item
     */
    void addItem(CatalogItem item);

    /**
     * Delete catalog item
     *
     * @param id catalog item id
     */
    void deleteItem(Long id);

    /**
     * Returns brand by given id.
     *
     * @param id brand id
     * @return item brand
     */
    Optional<Brand> getBrandById(Long id);

    /**
     * Returns available brands.
     *
     * @return items brands
     */
    Iterable<Brand> getAllBrands();

    /**
     * Returns category by given id.
     *
     * @param id category id
     * @return item category
     */
    Optional<Category> getCategoryById(Long id);

    /**
     * Returns available categories.
     *
     * @return item categories
     */
    Iterable<Category> getAllCategories();
}
