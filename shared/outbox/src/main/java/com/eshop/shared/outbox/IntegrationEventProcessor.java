package com.eshop.shared.outbox;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class IntegrationEventProcessor {
  private static final Logger logger = LoggerFactory.getLogger(IntegrationEventProcessor.class);

  private final IntegrationEventLogService integrationEventLogService;
  private final IntegrationEventPublisher integrationEventPublisher;

  @Scheduled(fixedDelay = 2000)
  public void process() {
    var eventLogEntries = integrationEventLogService.retrieveEventLogsPendingToPublish();

    if (eventLogEntries.size() > 0) {
      logger.info("{} integration events are ready to be published", eventLogEntries.size());
      eventLogEntries.forEach(this::publish);
    } else {
      logger.info("No integration events found to publish");
    }
  }

  private void publish(IntegrationEventLogEntry eventLogEntry) {
    integrationEventLogService.markEventAsInProgress(eventLogEntry);
    integrationEventPublisher.publish(eventLogEntry);
    integrationEventLogService.markEventAsPublished(eventLogEntry);
  }
}
