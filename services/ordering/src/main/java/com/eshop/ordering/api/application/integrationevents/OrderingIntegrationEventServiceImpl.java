package com.eshop.ordering.api.application.integrationevents;

import com.eshop.eventbus.IntegrationEvent;
import com.eshop.integrationeventlog.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderingIntegrationEventServiceImpl implements OrderingIntegrationEventService {
  private static final Logger logger = LoggerFactory.getLogger(OrderingIntegrationEventServiceImpl.class);

  private final IntegrationEventLogService eventLogService;
  private final EventBus eventBus;

  @Override
  public void publishEventsThroughEventBus(String id) {
    var pendingLogEvents = eventLogService.retrieveEventLogsPendingToPublish(id);

    for (var logEvt : pendingLogEvents) {
      try {
        eventLogService.markEventAsInProgress(logEvt.getEvent().getId());
        eventBus.publish(logEvt.getTopic(), logEvt.getEvent());
        eventLogService.markEventAsPublished(logEvt.getEvent().getId());
      } catch (Exception ex) {
        logger.error("Publishing integration event: {}", logEvt.getClass().getSimpleName());

        eventLogService.markEventAsFailed(logEvt.getEvent().getId());
      }
    }
  }

  @Override
  public void addAndSaveEvent(String topic, IntegrationEvent evt) {
    logger.info("Enqueuing integration event {} ({})", evt.getId(), evt.getClass().getSimpleName());

    eventLogService.saveEvent(evt, topic, Thread.currentThread().getName());
  }
}
