package com.eshop.integrationeventlog;

import com.eshop.eventbus.IntegrationEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InMemoryIntegrationEventLogService implements IntegrationEventLogService {
  private static final Logger logger = LoggerFactory.getLogger(InMemoryIntegrationEventLogService.class);

  private final Map<UUID, IntegrationEventLogEntry> integrationEvents = new LinkedHashMap<>();

  @Override
  public List<IntegrationEventLogEntry> retrieveEventLogsPendingToPublish(String transactionId) {
    return integrationEvents.values().stream()
        .filter(e -> e.getTransactionId().equals(transactionId))
        .peek(IntegrationEventLogEntry::deserialize)
        .collect(Collectors.toList());
  }

  @Override
  public void markEventAsInProgress(UUID eventId) {
    updateEventStatus(eventId, EventState.InProgress);
  }

  @Override
  public void markEventAsPublished(UUID eventId) {
    updateEventStatus(eventId, EventState.Published);
  }

  @Override
  public void markEventAsFailed(UUID eventId) {
    updateEventStatus(eventId, EventState.PublishedFailed);
  }

  @Override
  public void saveEvent(IntegrationEvent event, String topic, String transactionId) {
    if (transactionId == null) throw new IllegalArgumentException("transaction id cannot be null");

    try {
      var eventLogEntry = new IntegrationEventLogEntry(event, topic, transactionId);
      integrationEvents.put(event.getId(), eventLogEntry);
    } catch (JsonProcessingException e) {
      logger.error("Error while creating IntegrationEventLogEntry for {}: ", event.getClass().getSimpleName(), e);
    }
  }

  private void updateEventStatus(UUID eventId, EventState eventState) {
    var eventLogEntry = integrationEvents.get(eventId);
    eventLogEntry.setEventState(eventState);

    if (EventState.InProgress.equals(eventState))
      eventLogEntry.incrementTimesSent();

    integrationEvents.put(eventId, eventLogEntry);
  }
}
