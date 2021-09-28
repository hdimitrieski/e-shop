package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.aggregatesmodel.order.rules.UnitsPriceMustBeGreaterThanDiscount;
import com.eshop.ordering.domain.aggregatesmodel.order.snapshot.OrderItemSnapshot;
import com.eshop.ordering.domain.base.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.UUID;

public class OrderItem extends Entity<OrderItemId> {
  private final String productName;
  private Price discount;
  @Getter
  private final String pictureUrl;
  @Getter
  private final Price unitPrice;
  @Getter
  private Units units;
  @Getter
  private final UUID productId;

  private OrderItem(
      @NonNull OrderItemId id,
      @NonNull UUID productId,
      @NonNull String productName,
      @NonNull Price unitPrice,
      @NonNull Price discount,
      @NonNull Units units,
      String pictureUrl
  ) {
    Objects.requireNonNull(id, "Id cannot be null");
    Objects.requireNonNull(productId, "Product id cannot be null");
    Objects.requireNonNull(productName, "Product name cannot be null");
    Objects.requireNonNull(unitPrice, "Unit price cannot be null");
    Objects.requireNonNull(discount, "Discount cannot be null");
    Objects.requireNonNull(units, "Units cannot be null");
    checkRule(new UnitsPriceMustBeGreaterThanDiscount(unitPrice, units, discount));

    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.unitPrice = unitPrice;
    this.discount = discount;
    this.units = units;
    this.pictureUrl = pictureUrl;
  }

  @Builder(access = AccessLevel.PACKAGE)
  private OrderItem(UUID productId, String productName, Price unitPrice, Price discount, String pictureUrl, Units units) {
    this(OrderItemId.of(UUID.randomUUID()), productId, productName, unitPrice, discount, units, pictureUrl);
  }

  @NonNull
  public static OrderItem rehydrate(@NonNull OrderItemSnapshot snapshot) {
    Objects.requireNonNull(snapshot, "Snapshot cannot be null");

    return new OrderItem(
        OrderItemId.of(snapshot.getId()),
        snapshot.getProductId(),
        snapshot.getProductName(),
        Price.of(snapshot.getUnitPrice()),
        Price.of(snapshot.getDiscount()),
        Units.of(snapshot.getUnits()),
        snapshot.getPictureUrl()
    );
  }

  @NonNull
  public Price currentDiscount() {
    return discount;
  }

  @NonNull
  public String orderItemProductName() {
    return productName;
  }

  public void newDiscount(@NonNull Price discount) {
    Objects.requireNonNull(discount, "Discount cannot be null");
    this.discount = discount;
  }

  public void addUnits(@NonNull Units units) {
    Objects.requireNonNull(units, "Units cannot be null");
    this.units = this.units.add(units);
  }

  @NonNull
  @Override
  public OrderItemSnapshot snapshot() {
    return OrderItemSnapshot.builder()
        .id(id.getUuid())
        .discount(discount.getValue())
        .pictureUrl(pictureUrl)
        .productId(productId)
        .productName(productName)
        .unitPrice(unitPrice.getValue())
        .units(units.getValue())
        .build();
  }
}
