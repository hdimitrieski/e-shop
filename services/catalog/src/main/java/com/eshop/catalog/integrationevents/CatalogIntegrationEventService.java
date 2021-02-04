package com.eshop.catalog.integrationevents;

import com.eshop.catalog.shared.IntegrationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class CatalogIntegrationEventService implements IntegrationEventService {
    private final EntityManager entityManager;
    private final KafkaTemplate<Object, Object> kafkaTemplate;
    private final String catalogTopic;

    public CatalogIntegrationEventService(
            EntityManager entityManager,
            KafkaTemplate<Object, Object> kafkaTemplate,
            @Value("${spring.kafka.consumer.topic.catalog}") String catalogTopic
    ) {
        this.entityManager = entityManager;
        this.kafkaTemplate = kafkaTemplate;
        this.catalogTopic = catalogTopic;
    }

    @Override
    public void saveEventAndCatalogContextChanges(IntegrationEvent event) {
        System.out.printf("----- ----- CatalogIntegrationEventService - Saving changes and integrationEvent: %s (%s)", event.getId(), event.getClass().getSimpleName());
        // TODO HD see ResilientTransaction on eShopOnContainers (BuildingBlocks)
        // TODO HD Achieving atomicity between original catalog database operation and the IntegrationEventLog thanks to a local transaction
        // TODO HD save the event in a event log
        // _eventLogService.SaveEventAsync(evt, _catalogContext.Database.CurrentTransaction);
        // entityManager.getTransaction().commit();
    }

    @Override
    public void publishThroughEventBus(IntegrationEvent event) {
        // await _eventLogService.MarkEventAsInProgressAsync(evt.Id);
        System.out.printf("----- ----- Publishing integration event: %s (%s)", event.getId(), event.getClass().getSimpleName());
        kafkaTemplate.send(catalogTopic, event);
        // await _eventLogService.MarkEventAsPublishedAsync(evt.Id);
    }
}
