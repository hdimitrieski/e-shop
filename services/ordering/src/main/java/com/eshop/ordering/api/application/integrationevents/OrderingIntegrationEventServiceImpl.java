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
          "----- Publishing integration event: {%s} - (%s)\n", logEvt.getId(), logEvt.getClass().getSimpleName());

      try {
        eventLogService.markEventAsInProgress(logEvt.getId());
        eventBus.publish(logEvt);
        eventLogService.markEventAsPublished(logEvt.getId());
      } catch (Exception ex) {
        System.out.printf("ERROR publishing integration event: {%s}\n", logEvt.getClass().getSimpleName());

        eventLogService.markEventAsFailed(logEvt.getId());
      }
    }
  }

  @Override
  public void addAndSaveEvent(IntegrationEvent evt) {
    System.out.printf("----- Enqueuing integration event {%s} to repository ({%s})", evt.getId(), evt.getClass().getSimpleName());

    eventLogService.saveEvent(evt, Thread.currentThread().getId());
//    eventLogService.saveEvent(evt, integrationEventIdGenerator.transactionId());
  }
}
