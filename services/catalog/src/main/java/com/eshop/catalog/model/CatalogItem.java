package com.eshop.catalog.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder(toBuilder = true)
@Entity
@Table(name = "catalog_item")
public class CatalogItem extends AbstractEntity {
    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "picture_file_name")
    private String pictureFileName;

    // Quantity in stock
    @Column(name = "available_stock")
    private Integer availableStock;

    // Available stock at which we should reorder
    @Column(name = "restock_threshold")
    private Integer restockThreshold;

    // Maximum number of units that can be in-stock at any time (due to physicial/logistical constraints in warehouses)
    @Column(name = "max_stock_threshold")
    private Integer maxStockThreshold;

    // True if the item is on reorder
    @Column(name = "on_reorder")
    private boolean onReorder = false;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    /**
     * If there is sufficient stock of an item, then the integer returned at the end of this call should be the same as
     * quantityDesired.
     * In the event that there is not sufficient stock available, the method will remove whatever stock is available
     * and return that quantity to the client.
     * In this case, it is the responsibility of the client to determine if the amount that is returned is the same as
     * quantityDesired.
     *
     * @return Returns the number actually removed from stock.
     */
    public int removeStock(int quantityDesired) {
        if (availableStock == 0) {
            throw new CatalogDomainException("Empty stock, product item %s is sold out".formatted(name));
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
    public int addStock(int quantity) {
        int original = availableStock;

        // The quantity that the client is trying to add to stock is greater than what can be physically accommodated
        // in the Warehouse
        if ((availableStock + quantity) > maxStockThreshold) {
            // For now, this method only adds new units up maximum stock threshold.
            // In an expanded version of this application, we could include tracking for the remaining units and store
            // information about overstock elsewhere.
            availableStock += (maxStockThreshold - availableStock);
        } else {
            availableStock += quantity;
        }

        onReorder = false;

        return availableStock - original;
    }
}
