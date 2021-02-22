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

  // TODO HD generate unique id per user request to save all events with in the event log.

  @Override
  public void publishEventsThroughEventBus(UUID id) {
    var pendingLogEvents = eventLogService.retrieveEventLogsPendingToPublish(id);

    for (var logEvt : pendingLogEvents) {
      System.out.printf(
          "----- Publishing integration event: {%s} - (%s)\n", logEvt.getId(), logEvt.getClass().getSimpleName());

      try {
        eventLogService.markEventAsInProgress(logEvt.getId());
//        eventBus.Publish(logEvt.IntegrationEvent);
        eventLogService.markEventAsPublished(logEvt.getId());
      } catch (Exception ex) {
//        _logger.LogError(ex, "ERROR publishing integration event: {IntegrationEventId} from {AppName}", logEvt.EventId, Program.AppName);

        eventLogService.markEventAsFailed(logEvt.getId());
      }
    }
  }

  @Override
  public void addAndSaveEvent(IntegrationEvent evt) {
    System.out.printf("----- Enqueuing integration event {%s} to repository ({%s})", evt.getId(), evt.getClass().getSimpleName());

    eventLogService.saveEvent(evt, integrationEventIdGenerator.transactionId());
  }
}
