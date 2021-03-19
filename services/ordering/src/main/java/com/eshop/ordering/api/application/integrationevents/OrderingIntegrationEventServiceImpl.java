package com.eshop.ordering.api.application.integrationevents;

import com.eshop.ordering.api.application.IntegrationEventIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderingIntegrationEventServiceImpl implements OrderingIntegrationEventService {
  private final EventLogService eventLogService;
  private final IntegrationEventIdGenerator integrationEventIdGenerator;
  private final EventBus eventBus;

  @Override
  public void publishEventsThroughEventBus(long id) {
    var pendingLogEvents = eventLogService.retrieveEventLogsPendingToPublish(id);

    for (var logEvt : pendingLogEvents) {
      System.out.printf(
          "----- Publishing integration event: {%s} - (%s)\n", logEvt.event().getId(), logEvt.event().getClass().getSimpleName());

      try {
        eventLogService.markEventAsInProgress(logEvt.event().getId());
        eventBus.publish(logEvt.topic(), logEvt.event());
        eventLogService.markEventAsPublished(logEvt.event().getId());
      } catch (Exception ex) {
        System.out.printf("ERROR publishing integration event: {%s}\n", logEvt.getClass().getSimpleName());

        eventLogService.markEventAsFailed(logEvt.event().getId());
      }
    }
  }

  @Override
  public void addAndSaveEvent(String topic, IntegrationEvent evt) {
    System.out.printf("----- Enqueuing integration event {%s} to repository ({%s})", evt.getId(), evt.getClass().getSimpleName());

    eventLogService.saveEvent(evt, topic, Thread.currentThread().getId());
//    eventLogService.saveEvent(evt, integrationEventIdGenerator.transactionId());
  }
}
