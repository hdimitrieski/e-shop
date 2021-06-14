package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import com.eshop.ordering.domain.seedwork.Entity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@javax.persistence.Entity
public class OrderItem extends Entity {
  @Column(name = "product_name", nullable = false)
  private String productName;

  @Getter
  @Column(name = "picture_url")
  private String pictureUrl;

  @Getter
  @Column(name = "unit_price", nullable = false)
  private Double unitPrice;

  @Column(name = "discount")
  private Double discount;

  @Getter
  @Column(name = "units", nullable = false)
  private Integer units;

  @Getter
  @Column(name = "product_id", nullable = false)
  private Long productId;

  @ManyToOne(targetEntity = Order.class)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  protected OrderItem() {
  }

  public OrderItem(Long productId, String productName, Double unitPrice, Double discount, String PictureUrl, Order order, Integer units) {
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
    this.order = order;
  }

  public OrderItem(Long productId, String productName, Double unitPrice, Double discount, String pictureUrl, Order order) {
    this(productId, productName, unitPrice, discount, pictureUrl, order, 1);
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
