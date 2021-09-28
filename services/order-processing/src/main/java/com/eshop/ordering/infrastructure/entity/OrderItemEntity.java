package com.eshop.ordering.infrastructure.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(name = "order_item")
class OrderItemEntity extends DbEntity {
  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "picture_url")
  private String pictureUrl;

  @Column(name = "unit_price", nullable = false)
  private Double unitPrice;

  @Column(name = "discount")
  private Double discount;

  @Column(name = "units", nullable = false)
  private Integer units;

  @Column(name = "product_id", nullable = false)
  private UUID productId;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity order;
}
