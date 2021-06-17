package com.eshop.catalog.integrationevents;

import com.eshop.shared.eventhandling.IntegrationEvent;
import com.eshop.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CatalogIntegrationEventService implements IntegrationEventService {
  private static final Logger logger = LoggerFactory.getLogger(CatalogIntegrationEventService.class);

  private final IntegrationEventLogService eventLogService;

  @Override
  public void saveEventAndCatalogContextChanges(String topic, IntegrationEvent event) {
    logger.info("Saving changes and integrationEvent: {} ({})", event.getId(), event.getClass().getSimpleName());
    eventLogService.saveEvent(event, topic);
  }

}
