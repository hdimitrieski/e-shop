package com.eshop.shared.outbox;

import com.eshop.shared.eventhandling.IntegrationEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class IntegrationEventLogServiceImpl implements IntegrationEventLogService {
  private static final Logger logger = LoggerFactory.getLogger(IntegrationEventLogServiceImpl.class);

  private final ObjectMapper eventLogObjectMapper;
  private final IntegrationEventLogRepository integrationEventLogRepository;

  @Override
  public List<IntegrationEventLogEntry> retrieveEventLogsPendingToPublish() {
    return integrationEventLogRepository.findAll()
        .stream()
        .filter(eventLogEntry -> EventState.NotPublished.equals(eventLogEntry.getEventState()))
        .peek(eventLogEntry -> eventLogEntry.setEvent(this.deserialize(eventLogEntry)))
        .collect(Collectors.toList());
  }

  @Override
  public void markEventAsInProgress(IntegrationEventLogEntry eventLogEntry) {
    updateEventStatus(eventLogEntry, EventState.InProgress);
  }

  @Override
  public void markEventAsPublished(IntegrationEventLogEntry eventLogEntry) {
    updateEventStatus(eventLogEntry, EventState.Published);
  }

  @Override
  public void markEventAsFailed(IntegrationEventLogEntry eventLogEntry) {
    updateEventStatus(eventLogEntry, EventState.PublishedFailed);
  }

  @Override
  public void saveEvent(IntegrationEvent event, String topic) {
    if (event == null) throw new IllegalArgumentException("event cannot be null");
    if (topic == null) throw new IllegalArgumentException("topic cannot be null");

    try {
      var eventLogEntry = new IntegrationEventLogEntry(event, eventLogObjectMapper.writeValueAsString(event), topic);
      integrationEventLogRepository.save(eventLogEntry);
    } catch (JsonProcessingException e) {
      logger.error("Error while creating IntegrationEventLogEntry for {}: ", event.getClass().getSimpleName(), e);
    }
  }

  private void updateEventStatus(IntegrationEventLogEntry eventLogEntry, EventState eventState) {
    eventLogEntry.setEventState(eventState);

    if (EventState.InProgress.equals(eventState))
      eventLogEntry.incrementTimesSent();

    integrationEventLogRepository.save(eventLogEntry);
  }

  @SneakyThrows
  private IntegrationEvent deserialize(IntegrationEventLogEntry eventLogEntry) {
    return (IntegrationEvent) eventLogObjectMapper.readValue(
        eventLogEntry.getContent(),
        Class.forName(eventLogEntry.getEventTypeName())
    );
  }
}
