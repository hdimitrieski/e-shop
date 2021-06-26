package com.eshop.ordering.infrastructure.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Getter
@Entity
@Table(name = "orders")
class OrderEntity extends DbEntity {
  @Column(nullable = false)
  private LocalDateTime orderDate;

  @Embedded
  private AddressModel address;

  @Column(name = "buyer_id", nullable = false)
  private UUID buyerId;

  @Column(name = "order_status", nullable = false)
  private String orderStatus;

  @Column(name = "description")
  private String description;

  @Column(name = "is_draft")
  private boolean isDraft;

  @Column(name = "payment_method_id", nullable = false)
  private UUID paymentMethodId;

  @Setter
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
  private Set<OrderItemEntity> orderItems;
}
