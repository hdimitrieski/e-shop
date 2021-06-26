package com.eshop.ordering.infrastructure.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(name = "payment_method")
public class PaymentMethodEntity extends DbEntity {
  @Column(name = "alias", nullable = false, length = 200)
  private String alias;

  @Column(name = "card_number", nullable = false, length = 25)
  private String cardNumber;

  @Column(name = "security_number", nullable = false, length = 200)
  private String securityNumber;

  @Column(name = "card_holder_name", nullable = false, length = 200)
  private String cardHolderName;

  @Column(name = "expiration", nullable = false)
  private LocalDate expiration;

  @Column(name = "card_type", nullable = false)
  private String cardType;

  @Setter
  @ManyToOne
  @JoinColumn(name = "buyer_id", nullable = false)
  private BuyerEntity buyer;
}
