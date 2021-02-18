package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import com.eshop.ordering.domain.seedwork.Entity;
import lombok.Getter;

public class OrderItem extends Entity {
    private String productName;
    @Getter
    private String pictureUrl;
    @Getter
    private Double unitPrice;
    private Double discount;
    @Getter
    private Integer units;
    @Getter
    private Integer productId;

    protected OrderItem() { }

    public OrderItem(Integer productId, String productName, Double unitPrice, Double discount, String PictureUrl, Integer units) {
        if (units <= 0) {
            throw new OrderingDomainException("Invalid number of units");
        }

        if ((unitPrice * units) < discount) {
            throw new OrderingDomainException("The total of order item is lower than applied discount");
        }

        this.productId = productId;

        this.productName = productName;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.units = units;
        this.pictureUrl = PictureUrl;
    }

    public OrderItem(Integer productId, String productName, Double unitPrice, Double discount, String pictureUrl) {
        this(productId, productName, unitPrice, discount, pictureUrl, 1);
    }

    public Double getCurrentDiscount() {
        return discount;
    }

    public String getOrderItemProductName() {
        return productName;
    }

    public void setNewDiscount(Double discount) {
        if (discount < 0) {
            throw new OrderingDomainException("Discount is not valid");
        }

        this.discount = discount;
    }

    public void addUnits(Integer units) {
        if (units < 0) {
            throw new OrderingDomainException("Invalid units");
        }

        this.units += units;
    }
}
