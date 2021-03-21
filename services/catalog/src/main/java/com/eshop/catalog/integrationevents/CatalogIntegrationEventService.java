package com.eshop.catalog.integrationevents;

import com.eshop.eventbus.IntegrationEvent;
import com.eshop.integrationeventlog.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CatalogIntegrationEventService implements IntegrationEventService {
  private static final Logger logger = LoggerFactory.getLogger(CatalogIntegrationEventService.class);

  private final IntegrationEventLogService eventLogService;
  private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

  @Override
  public void saveEventAndCatalogContextChanges(String topic, IntegrationEvent event) {
    logger.info("Saving changes and integrationEvent: {} ({})", event.getId(), event.getClass().getSimpleName());
    // TODO HD see ResilientTransaction on eShopOnContainers (BuildingBlocks)
    // TODO HD Achieving atomicity between original catalog database operation and the IntegrationEventLog thanks to a local transaction
    // TODO HD save the event in a event log
    // TODO HD use ChainedTransactionManager to manage two transactions: one for catalog, one for log
    // https://blog.usejournal.com/springboot-jpa-rollback-transaction-with-multi-databases-53e6f2f143d6
    // https://github.com/jmgoyesc/demo.multiple.datasources
    // https://www.geekyhacker.com/2019/10/07/spring-transaction-with-multiple-datasources/
    // https://github.com/kasramp/spring-multiple-datasources
    eventLogService.saveEvent(event, topic, Thread.currentThread().getName());
  }

  @Override
  public void publishThroughEventBus(String topic, IntegrationEvent event) {
    logger.info("Publishing integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    eventLogService.markEventAsInProgress(event.getId());
    kafkaTemplate.send(topic, event);
    eventLogService.markEventAsPublished(event.getId());
  }
}
