package com.eshop.integrationeventlog;

import com.eshop.eventbus.IntegrationEvent;

import java.util.List;
import java.util.UUID;

public interface IntegrationEventLogService {
  List<IntegrationEventLogEntry> retrieveEventLogsPendingToPublish(String transactionId);

  void markEventAsInProgress(UUID eventId);

  void markEventAsPublished(UUID eventId);

  void markEventAsFailed(UUID eventId);

  void saveEvent(IntegrationEvent event, String topic, String transactionId);
}
