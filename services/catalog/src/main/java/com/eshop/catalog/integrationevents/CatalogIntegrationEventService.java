package com.eshop.catalog.integrationevents;

import com.eshop.catalog.integrationevents.eventhandling.OrderStatusChangedToAwaitingValidationIntegrationEventHandler;
import com.eshop.eventbus.IntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Service
public class CatalogIntegrationEventService implements IntegrationEventService {
  private static final Logger logger = LoggerFactory.getLogger(CatalogIntegrationEventService.class);

  private final EntityManager entityManager;
  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

  @Override
  public void saveEventAndCatalogContextChanges(IntegrationEvent event) {
    logger.info("Saving changes and integrationEvent: {} ({})", event.getId(), event.getClass().getSimpleName());
    // TODO HD see ResilientTransaction on eShopOnContainers (BuildingBlocks)
    // TODO HD Achieving atomicity between original catalog database operation and the IntegrationEventLog thanks to a local transaction
    // TODO HD save the event in a event log
    // _eventLogService.SaveEventAsync(evt, _catalogContext.Database.CurrentTransaction);
    // entityManager.getTransaction().commit();
  }

  @Override
  public void publishThroughEventBus(String topic, IntegrationEvent event) {
    // await _eventLogService.MarkEventAsInProgressAsync(evt.Id);
    logger.info("Publishing integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    kafkaTemplate.send(topic, event);
    // await _eventLogService.MarkEventAsPublishedAsync(evt.Id);
  }
}
