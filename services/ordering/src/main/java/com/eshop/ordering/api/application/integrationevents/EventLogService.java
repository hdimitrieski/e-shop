package com.eshop.ordering.api.application.integrationevents;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Stores events by transaction (request in our case). Every event that is published in a single request will be
 * stored with the same id.
 */
@Service
public class EventLogService {

  private final Map<UUID, List<IntegrationEvent>> integrationEvents = new HashMap<>();

  public List<IntegrationEvent> retrieveEventLogsPendingToPublish(UUID transactionId) {
    return integrationEvents.get(transactionId);
  }

  public void markEventAsInProgress(UUID eventId) {

  }

  public void markEventAsPublished(UUID eventId) {

  }

  public void markEventAsFailed(UUID eventId) {

  }

  public void saveEvent(IntegrationEvent event, UUID transactionId) {
    var events = integrationEvents.containsKey(transactionId)
        ? integrationEvents.get(transactionId)
        : new ArrayList<IntegrationEvent>();
    events.add(event);
    integrationEvents.put(transactionId, events);
  }
}
