package com.eshop.ordering.api.application.integrationevents;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventLogService {

  private final Map<UUID, List<IntegrationEvent>> integrationEvents = new HashMap<>();

  public List<IntegrationEvent> retrieveEventLogsPendingToPublish(UUID id) {
    return integrationEvents.get(id);
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

    integrationEvents.put(transactionId, events);
  }
}
