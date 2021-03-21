package com.eshop.integrationeventlog;

import com.eshop.eventbus.IntegrationEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class IntegrationEventLogEntry {
  private final UUID eventId;
  private final LocalDateTime creationTime;
  private final String eventTypeName;
  private final String content;
  @Setter
  private EventState eventState;
  private Integer timesSent;
  private final String topic;
  private final String transactionId;
  private IntegrationEvent event;

  IntegrationEventLogEntry(IntegrationEvent event, String topic, String transactionId) throws JsonProcessingException {
    eventId = event.getId();
    creationTime = LocalDateTime.now();
    eventTypeName = event.getClass().getName();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    content = objectMapper.writeValueAsString(event);
    eventState = EventState.NotPublished;
    timesSent = 0;
    this.topic = topic;
    this.transactionId = transactionId;
  }

  void incrementTimesSent() {
    this.timesSent++;
  }

  @SneakyThrows
  void deserialize() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    this.event = (IntegrationEvent) objectMapper.readValue(this.content, Class.forName(this.eventTypeName));
  }
}
