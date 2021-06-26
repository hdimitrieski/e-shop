package com.eshop.ordering.infrastructure.entity;

import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.aggregatesmodel.order.snapshot.OrderItemSnapshot;
import com.eshop.ordering.domain.aggregatesmodel.order.snapshot.OrderSnapshot;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
class OrderEntityConverter implements EntityConverter<OrderEntity, Order> {

  @Override
  public OrderEntity toEntity(Order order) {
    var snapshot = order.snapshot();

    var orderEntity = OrderEntity.builder()
        .id(UUID.fromString(snapshot.getId()))
        .orderDate(snapshot.getOrderDate())
        .buyerId(snapshot.getBuyerId() != null ? UUID.fromString(snapshot.getBuyerId()) : null)
        .description(snapshot.getDescription())
        .isDraft(snapshot.isDraft())
        .paymentMethodId(snapshot.getPaymentMethodId() != null ? UUID.fromString(snapshot.getPaymentMethodId()) : null)
        .orderStatus(order.getOrderStatus().getStatus())
        .address(AddressModel.builder()
            .city(snapshot.getCity())
            .country(snapshot.getCountry())
            .state(snapshot.getState())
            .street(snapshot.getStreet())
            .zipCode(snapshot.getZipCode())
            .build())
        .build();

    orderEntity.setOrderItems(snapshot.getOrderItems()
        .stream()
        .map(orderItemSnapshot -> toOrderItemEntity(orderItemSnapshot, orderEntity))
        .collect(Collectors.toSet()));

    return orderEntity;
  }

  private OrderItemEntity toOrderItemEntity(OrderItemSnapshot snapshot, OrderEntity orderEntity) {
    return OrderItemEntity.builder()
        .id(UUID.fromString(snapshot.getId()))
        .discount(snapshot.getDiscount())
        .pictureUrl(snapshot.getPictureUrl())
        .productId(snapshot.getProductId())
        .productName(snapshot.getProductName())
        .unitPrice(snapshot.getUnitPrice())
        .units(snapshot.getUnits())
        .order(orderEntity)
        .build();
  }

  @Override
  public Order fromEntity(OrderEntity orderEntity) {
    return Order.rehydrate(OrderSnapshot.builder()
        .id(orderEntity.getId().toString())
        .orderStatus(orderEntity.getOrderStatus())
        .orderDate(orderEntity.getOrderDate())
        .draft(orderEntity.isDraft())
        .paymentMethodId(orderEntity.getPaymentMethodId() != null ? orderEntity.getPaymentMethodId().toString() : null)
        .buyerId(orderEntity.getBuyerId() != null ? orderEntity.getBuyerId().toString() : null)
        .city(orderEntity.getAddress().getCity())
        .street(orderEntity.getAddress().getStreet())
        .zipCode(orderEntity.getAddress().getZipCode())
        .state(orderEntity.getAddress().getState())
        .country(orderEntity.getAddress().getCountry())
        .orderItems(orderEntity.getOrderItems().stream()
            .map(this::toOrderItemSnapshot)
            .collect(Collectors.toList()))
        .build());
  }

  private OrderItemSnapshot toOrderItemSnapshot(OrderItemEntity orderItemEntity) {
    return OrderItemSnapshot.builder()
        .id(orderItemEntity.getId().toString())
        .units(orderItemEntity.getUnits())
        .productId(orderItemEntity.getProductId())
        .unitPrice(orderItemEntity.getUnitPrice())
        .productName(orderItemEntity.getProductName())
        .pictureUrl(orderItemEntity.getPictureUrl())
        .discount(orderItemEntity.getDiscount())
        .build();
  }

}
