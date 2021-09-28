package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.aggregatesmodel.buyer.*;
import com.eshop.ordering.domain.aggregatesmodel.order.rules.OrderMustBePaid;
import com.eshop.ordering.domain.aggregatesmodel.order.rules.OrderMustNotBePaidOrShipped;
import com.eshop.ordering.domain.aggregatesmodel.order.snapshot.OrderSnapshot;
import com.eshop.ordering.domain.base.Identifier;
import com.eshop.ordering.domain.events.*;
import com.eshop.ordering.domain.base.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class Order extends AggregateRoot<OrderId> {

  private LocalDateTime orderDate;
  private String description;
  private boolean draft;
  @Getter
  private Address address;
  @Getter
  private OrderStatus orderStatus;
  @Setter
  private BuyerId buyerId;
  @Setter
  private PaymentMethodId paymentMethodId;

  /*
   * Using a private collection field, better for DDD Aggregate's encapsulation so OrderItems cannot be added from
   * "outside the AggregateRoot" directly to the collection, but only through the method Order.addOrderItem() which
   * includes behaviour.
   */
  @Getter
  private List<OrderItem> orderItems;

  private Order() {
    orderItems = new ArrayList<>();
    draft = false;
  }

  private Order(
      @NonNull OrderId orderId,
      @NonNull Address address,
      @NonNull OrderStatus orderStatus,
      BuyerId buyerId,
      PaymentMethodId paymentMethodId,
      boolean draft,
      List<OrderItem> orderItems
  ) {
    this();
    Objects.requireNonNull(orderId, "Order id cannot be null");
    Objects.requireNonNull(address, "Address cannot be null");
    Objects.requireNonNull(orderStatus, "Order status cannot be null");

    this.id = orderId;
    this.buyerId = buyerId;
    this.paymentMethodId = paymentMethodId;
    this.orderDate = LocalDateTime.now();
    this.address = address;
    this.orderStatus = orderStatus;
    this.draft = draft;
    this.orderItems = orderItems;
  }

  @NonNull
  public static Order create(@NonNull NewOrderData orderData) {
    Objects.requireNonNull(orderData, "New order data cannot be null");
    var order = new Order(OrderId.of(UUID.randomUUID()), orderData.getAddress(), OrderStatus.Submitted, null, null, false, new ArrayList<>());
    /*
     * Add the OrderStarterDomainEvent to the domain events collection to be dispatched when committing changes
     * into the Database.
     */
    order.addOrderStartedDomainEvent(orderData);
    return order;
  }

  @NonNull
  public static Order rehydrate(@NonNull OrderSnapshot snapshot) {
    Objects.requireNonNull(snapshot, "Snapshot cannot be null");
    return new Order(
        OrderId.of(snapshot.getId()),
        Address.builder()
            .city(snapshot.getCity())
            .zipCode(snapshot.getZipCode())
            .country(snapshot.getCountry())
            .state(snapshot.getState())
            .street(snapshot.getStreet())
            .build(),
        OrderStatus.of(snapshot.getOrderStatus()),
        snapshot.getBuyerId() != null ? BuyerId.of(snapshot.getBuyerId()) : null,
        snapshot.getPaymentMethodId() != null ? PaymentMethodId.of(snapshot.getPaymentMethodId()) : null,
        snapshot.isDraft(),
        snapshot.getOrderItems().stream()
            .map(OrderItem::rehydrate)
            .collect(Collectors.toList())
    );
  }

  @NonNull
  public static Order newDraft() {
    var order = new Order();
    order.draft = true;
    return order;
  }

  public Optional<BuyerId> buyerId() {
    return Optional.ofNullable(buyerId);
  }

  public Optional<PaymentMethodId> paymentMethodId() {
    return Optional.ofNullable(paymentMethodId);
  }

  /*
   * DDD Patterns comment
   * This Order AggregateRoot's method "addOrderItem()" should be the only way to add Items to the Order, so any
   * behavior (discounts, etc.) and validations are controlled by the AggregateRoot in order to maintain consistency
   * between the whole Aggregate.
   */
  public void addOrderItem(@NonNull Product product) {
    Objects.requireNonNull(product, "Product cannot be null");

    orderItems.stream()
        .filter(o -> o.getProductId().equals(product.getProductId()))
        .findFirst()
        .ifPresentOrElse(
            existingOrderForProduct -> updateExistingOrderItem(existingOrderForProduct, product),
            () -> addNewOrderItem(product)
        );
  }

  public void setAwaitingValidationStatus() {
    if (OrderStatus.Submitted.equals(orderStatus)) {
      addDomainEvent(new OrderStatusChangedToAwaitingValidationDomainEvent(id, orderItems));
      changeOrderStatusTo(OrderStatus.AwaitingValidation);
    }
  }

  public void setStockConfirmedStatus() {
    if (OrderStatus.AwaitingValidation.equals(orderStatus)) {
      addDomainEvent(new OrderStatusChangedToStockConfirmedDomainEvent(id));
      changeOrderStatusTo(OrderStatus.StockConfirmed);
      description = "All the items were confirmed with available stock.";
    }
  }

  public void setPaidStatus() {
    if (OrderStatus.StockConfirmed.equals(orderStatus)) {
      changeOrderStatusTo(OrderStatus.Paid);
      description = "The order was paid";
      addDomainEvent(new OrderStatusChangedToPaidDomainEvent(id, orderItems));
    }
  }

  public void setShippedStatus() {
    checkRule(new OrderMustBePaid(orderStatus));
    changeOrderStatusTo(OrderStatus.Shipped);
    description = "The order was shipped.";
    addDomainEvent(new OrderShippedDomainEvent(this));
  }

  public void setCancelledStatus() {
    checkRule(new OrderMustNotBePaidOrShipped(orderStatus));
    changeOrderStatusTo(OrderStatus.Cancelled);
    description = "The order was cancelled.";
    addDomainEvent(new OrderCancelledDomainEvent(this));
  }

  public void setCancelledStatusWhenStockIsRejected(@NonNull List<UUID> orderStockRejectedItems) {
    if (OrderStatus.AwaitingValidation.equals(orderStatus)) {
      changeOrderStatusTo(OrderStatus.Cancelled);

      var itemsStockRejectedProductNames = orderItems.stream()
          .filter(c -> orderStockRejectedItems.contains(c.getProductId()))
          .map(OrderItem::orderItemProductName)
          .collect(Collectors.toList());

      var itemsStockRejectedDescription = String.join(", ", itemsStockRejectedProductNames);
      description = "The product items don't have stock: ({%s}).".formatted(itemsStockRejectedDescription);
    }
  }

  @NonNull
  public Price getTotal() {
    return orderItems.stream()
        .map(orderItem -> orderItem.getUnitPrice().multiply(orderItem.getUnits()))
        .reduce(Price::sum)
        .orElseGet(Price::empty);
  }

  @NonNull
  @Override
  public OrderSnapshot snapshot() {
    return OrderSnapshot.builder()
        .id(id.getUuid())
        .orderStatus(orderStatus.getStatus())
        .buyerId(buyerId().map(Identifier::getUuid).orElse(null))
        .paymentMethodId(paymentMethodId().map(Identifier::getUuid).orElse(null))
        .draft(draft)
        .orderDate(orderDate)
        .description(description)
        .city(address.getCity())
        .country(address.getCountry())
        .state(address.getState())
        .street(address.getStreet())
        .zipCode(address.getZipCode())
        .orderItems(orderItems.stream().map(OrderItem::snapshot).collect(Collectors.toList()))
        .build();
  }

  private void addOrderStartedDomainEvent(NewOrderData orderData) {
    var orderStartedDomainEvent = new OrderStartedDomainEvent(
        this,
        orderData.getUserId(),
        orderData.getBuyerName(),
        orderData.getCardType(),
        orderData.getCardNumber(),
        orderData.getCardSecurityNumber(),
        orderData.getCardHolderName(),
        orderData.getCardExpiration()
    );

    this.addDomainEvent(orderStartedDomainEvent);
  }

  private void updateExistingOrderItem(@NonNull OrderItem existingOrderItem, @NonNull Product product) {
    if (product.getDiscount().greaterThan(existingOrderItem.currentDiscount())) {
      existingOrderItem.newDiscount(product.getDiscount());
    }

    existingOrderItem.addUnits(product.getUnits());
  }

  private void addNewOrderItem(@NonNull Product product) {
    orderItems.add(
        OrderItem.builder()
            .productId(product.getProductId())
            .productName(product.getProductName())
            .unitPrice(product.getUnitPrice())
            .discount(product.getDiscount())
            .pictureUrl(product.getPictureUrl())
            .units(product.getUnits())
            .build());
  }

  private void changeOrderStatusTo(OrderStatus newOrderStatus) {
    this.orderStatus = newOrderStatus;
  }

}
