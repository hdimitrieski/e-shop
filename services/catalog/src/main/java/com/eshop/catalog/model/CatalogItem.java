package com.eshop.catalog.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Getter
public class CatalogItem extends AbstractEntity {
    @Column(length = 50, nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private String pictureFileName;
//    private String pictureUri;

    @ManyToOne
    private CatalogType catalogType;

    @ManyToOne
    private CatalogBrand catalogBrand;

    // Quantity in stock
    private Integer availableStock;

    // Available stock at which we should reorder
    private Integer restockThreshold;

    // Maximum number of units that can be in-stock at any time (due to physicial/logistical constraints in warehouses)
    private Integer maxStockThreshold;

    // True if the item is on reorder
    private boolean onReorder = false;

    /**
     * If there is sufficient stock of an item, then the integer returned at the end of this call should be the same as quantityDesired.
     * In the event that there is not sufficient stock available, the method will remove whatever stock is available and return that quantity to the client.
     * In this case, it is the responsibility of the client to determine if the amount that is returned is the same as quantityDesired.
     *
     * @return Returns the number actually removed from stock.
     */
    public int removeStock(int quantityDesired) {
        if (availableStock == 0) {
            throw new CatalogDomainException(String.format("Empty stock, product item %s is sold out", name));
        }

        if (quantityDesired <= 0) {
            throw new CatalogDomainException("Item units desired should be greater than zero");
        }

        int removed = Math.min(quantityDesired, availableStock);
        availableStock -= removed;
        return removed;
    }

    /**
     * Increments the quantity of a particular item in inventory.
     *
     * @return the quantity that has been added to stock
     */
    public int addStock(int quantity)
    {
        int original = availableStock;

        // The quantity that the client is trying to add to stock is greater than what can be physically accommodated in the Warehouse
        if ((availableStock + quantity) > maxStockThreshold) {
            // For now, this method only adds new units up maximum stock threshold. In an expanded version of this application, we
            // could include tracking for the remaining units and store information about overstock elsewhere.
            availableStock += (maxStockThreshold - availableStock);
        } else {
            availableStock += quantity;
        }

        onReorder = false;

        return availableStock - original;
    }
}
