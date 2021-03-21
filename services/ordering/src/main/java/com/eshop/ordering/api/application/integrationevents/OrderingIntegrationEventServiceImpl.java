package com.eshop.ordering.api.application.integrationevents;

import com.eshop.eventbus.IntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderingIntegrationEventServiceImpl implements OrderingIntegrationEventService {
  private static final Logger logger = LoggerFactory.getLogger(OrderingIntegrationEventServiceImpl.class);

  private final EventLogService eventLogService;
  private final EventBus eventBus;

  @Override
  public void publishEventsThroughEventBus(long id) {
    var pendingLogEvents = eventLogService.retrieveEventLogsPendingToPublish(id);

    for (var logEvt : pendingLogEvents) {
      logger.info("Publishing integration event: {} ({})", logEvt.event().getId(), logEvt.event().getClass().getSimpleName());

      try {
        eventLogService.markEventAsInProgress(logEvt.event().getId());
        eventBus.publish(logEvt.topic(), logEvt.event());
        eventLogService.markEventAsPublished(logEvt.event().getId());
      } catch (Exception ex) {
        logger.error("Publishing integration event: {}", logEvt.getClass().getSimpleName());

        eventLogService.markEventAsFailed(logEvt.event().getId());
      }
    }
  }

  @Override
  public void addAndSaveEvent(String topic, IntegrationEvent evt) {
    logger.info("Enqueuing integration event {} ({})", evt.getId(), evt.getClass().getSimpleName());

    eventLogService.saveEvent(evt, topic, Thread.currentThread().getId());
  }
}
