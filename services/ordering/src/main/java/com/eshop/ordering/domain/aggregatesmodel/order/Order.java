package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.events.*;
import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import com.eshop.ordering.domain.seedwork.AggregateRoot;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "orders")
public class Order extends AggregateRoot {
  @Column(nullable = false)
  private LocalDateTime orderDate;

  // Address is a Value Object pattern example
  @Embedded
  @Getter
  private Address address;

  @Getter
  @Setter
  private Long buyerId;

  @Transient
  @Getter
  private OrderStatus orderStatus;
  @Column(nullable = false)
  private Integer orderStatusId;

  private String description;

  // Draft orders have this set to true. Currently we don't check anywhere the draft status of an Order,
  // but we could do it if needed
  private boolean isDraft;

  // DDD Patterns comment
  // Using a private collection field, better for DDD Aggregate's encapsulation
  // so OrderItems cannot be added from "outside the AggregateRoot" directly to the collection,
  // but only through the method OrderAggrergateRoot.AddOrderItem() which includes behaviour.
  @OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL, mappedBy = "order")
  @Getter
  private List<OrderItem> orderItems;

  @Setter
  private Long paymentMethodId;

  public static Order newDraft() {
    var order = new Order();
    order.isDraft = true;
    return order;
  }

  protected Order() {
    orderItems = new ArrayList<>();
    isDraft = false;
  }

  public Order(String userId, String userName, Address address, Integer cardTypeId, String cardNumber, String cardSecurityNumber,
               String cardHolderName, LocalDate cardExpiration, Long buyerId, Long paymentMethodId) {
    this();
    this.buyerId = buyerId;
    this.paymentMethodId = paymentMethodId;
    this.orderDate = LocalDateTime.now();
    this.address = address;
    this.orderStatusId = OrderStatus.Submitted.getId();
    this.populateOrderStatus();

    // Add the OrderStarterDomainEvent to the domain events collection
    // to be raised/dispatched when comitting changes into the Database [ After DbContext.SaveChanges() ]
    addOrderStartedDomainEvent(userId, userName, cardTypeId, cardNumber,
        cardSecurityNumber, cardHolderName, cardExpiration);
  }

  public Order(String userId, String userName, Address address, Integer cardTypeId, String cardNumber, String cardSecurityNumber,
               String cardHolderName, LocalDate cardExpiration) {
    this(userId, userName, address, cardTypeId, cardNumber, cardSecurityNumber, cardHolderName, cardExpiration, null, null);
  }

  // DDD Patterns comment
  // This Order AggregateRoot's method "addOrderitem()" should be the only way to add Items to the Order,
  // so any behavior (discounts, etc.) and validations are controlled by the AggregateRoot
  // in order to maintain consistency between the whole Aggregate.
  public void addOrderItem(Integer productId, String productName, Double unitPrice, Double discount, String pictureUrl, Integer units) {
    var existingOrderForProduct = orderItems.stream().filter(o -> o.getProductId().equals(productId))
        .findFirst()
        .orElse(null);

    if (existingOrderForProduct != null) {
      //if previous line exist modify it with higher discount  and units..
      if (discount > existingOrderForProduct.getCurrentDiscount()) {
        existingOrderForProduct.setNewDiscount(discount);
      }
      existingOrderForProduct.addUnits(units);
    } else {
      //add validated new order item
      orderItems.add(new OrderItem(productId, productName, unitPrice, discount, pictureUrl, this, units));
    }
  }

  public void addOrderItem(Integer productId, String productName, Double unitPrice, Double discount, String pictureUrl) {
    addOrderItem(productId, productName, unitPrice, discount, pictureUrl, 1);
  }

  public void setAwaitingValidationStatus() {
    if (OrderStatus.Submitted.getId().equals(orderStatusId)) {
      addDomainEvent(new OrderStatusChangedToAwaitingValidationDomainEvent(id, orderItems));
      orderStatusId = OrderStatus.AwaitingValidation.getId();
    }
  }

  public void setStockConfirmedStatus() {
    if (OrderStatus.AwaitingValidation.getId().equals(orderStatusId)) {
      addDomainEvent(new OrderStatusChangedToStockConfirmedDomainEvent(id));

      orderStatusId = OrderStatus.StockConfirmed.getId();
      description = "All the items were confirmed with available stock.";
    }
  }

  public void setPaidStatus() {
    if (OrderStatus.StockConfirmed.getId().equals(orderStatusId)) {
      addDomainEvent(new OrderStatusChangedToPaidDomainEvent(id, orderItems));
      orderStatusId = OrderStatus.Paid.getId();
      description = "The payment was performed at a simulated \"American Bank checking bank account ending on XX35071\"";
    }
  }

  public void setShippedStatus() {
    if (!OrderStatus.Paid.getId().equals(orderStatusId)) {
      statusChangeException(OrderStatus.Shipped);
    }

    orderStatusId = OrderStatus.Shipped.getId();
    description = "The order was shipped.";
    addDomainEvent(new OrderShippedDomainEvent(this));
  }

  public void setCancelledStatus() {
    if (OrderStatus.Paid.getId().equals(orderStatusId) || OrderStatus.Shipped.getId().equals(orderStatusId)) {
      statusChangeException(OrderStatus.Cancelled);
    }

    orderStatusId = OrderStatus.Cancelled.getId();
    description = "The order was cancelled.";
    addDomainEvent(new OrderCancelledDomainEvent(this));
  }

  public void setCancelledStatusWhenStockIsRejected(List<Integer> orderStockRejectedItems) {
    if (OrderStatus.AwaitingValidation.getId().equals(orderStatusId)) {
      orderStatusId = OrderStatus.Cancelled.getId();

      var itemsStockRejectedProductNames = orderItems.stream()
          .filter(c -> orderStockRejectedItems.contains(c.getProductId()))
          .map(OrderItem::getOrderItemProductName)
          .collect(Collectors.toList());

      var itemsStockRejectedDescription = String.join(", ", itemsStockRejectedProductNames);
      description = "The product items don't have stock: ({%s}).".formatted(itemsStockRejectedDescription);
    }
  }

  private void addOrderStartedDomainEvent(String userId, String userName, Integer cardTypeId, String cardNumber,
                                          String cardSecurityNumber, String cardHolderName, LocalDate cardExpiration) {
    var orderStartedDomainEvent = new OrderStartedDomainEvent(this, userId, userName, cardTypeId,
        cardNumber, cardSecurityNumber, cardHolderName, cardExpiration);

    this.addDomainEvent(orderStartedDomainEvent);
  }

  private void statusChangeException(OrderStatus orderStatusToChange) {
    throw new OrderingDomainException("Is not possible to change the order status from {%s} to {%s}."
        .formatted(orderStatus.getName(), orderStatusToChange.getName()));
  }

  public Double getTotal() {
    return orderItems.stream().map(o -> o.getUnits() * o.getUnitPrice())
        .reduce(Double::sum)
        .orElse(0D);
  }

  @PostLoad
  void populateOrderStatus() {
    if (orderStatusId > 0) {
      this.orderStatus = OrderStatus.from(orderStatusId);
    }
  }

  @PrePersist
  void populateOrderStatusId() {
    if (orderStatus != null) {
      this.orderStatusId = orderStatus.getId();
    }
  }
}
