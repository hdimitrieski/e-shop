package com.eshop.ordering.api.application.integrationevents;

import com.eshop.eventbus.IntegrationEvent;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Stores events by transaction (request in our case). Every event that is published in a single request will be
 * stored with the same id.
 * TODO HD implement this properly and use it in the other services as well.
 */
@Service
public class EventLogService {

  private final Map<Long, List<LogEvent>> integrationEvents = new HashMap<>();

  public List<LogEvent> retrieveEventLogsPendingToPublish(long threadId) {
    return integrationEvents.getOrDefault(threadId, Collections.emptyList());
  }

  public void markEventAsInProgress(UUID eventId) {

  }

  public void markEventAsPublished(UUID eventId) {

  }

  public void markEventAsFailed(UUID eventId) {

  }

  public void saveEvent(IntegrationEvent event, String topic, long threadId) {
    var events = integrationEvents.containsKey(threadId)
        ? integrationEvents.get(threadId)
        : new ArrayList<LogEvent>();
    events.add(new LogEvent(event, topic));
    integrationEvents.put(threadId, events);
  }
}
