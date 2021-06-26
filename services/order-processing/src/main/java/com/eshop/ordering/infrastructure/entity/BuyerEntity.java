package com.eshop.ordering.infrastructure.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Entity
@Table(name = "buyer", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id"}))
class BuyerEntity extends DbEntity {

  @Column(name = "user_id", nullable = false, length = 200, unique = true)
  private String userId;

  @Column(name = "name", nullable = false)
  private String name;

  @Setter
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
  @OrderBy("id")
  private Set<PaymentMethodEntity> paymentMethods;
}
