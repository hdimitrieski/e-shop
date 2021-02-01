package com.eshop.catalog.integrationevents.eventhandling;

import com.eshop.catalog.integrationevents.events.OrderStatusChangedToPaidIntegrationEvent;
import com.eshop.catalog.model.CatalogItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@AllArgsConstructor
@Component
public class OrderStatusChangedToPaidIntegrationEventHandler {
    private final CatalogItemRepository catalogItemRepository;

    @KafkaListener(topics = "${spring.kafka.consumer.topic.order}")
    @Transactional
    public void handle(OrderStatusChangedToPaidIntegrationEvent event, Acknowledgment acknowledgment) {
        for (var orderStockItem : event.getOrderStockItems()) {
            catalogItemRepository.findById(orderStockItem.productId()).ifPresent(catalogItem ->
                catalogItem.removeStock(orderStockItem.units())
            );
        }
        acknowledgment.acknowledge();
    }
}
