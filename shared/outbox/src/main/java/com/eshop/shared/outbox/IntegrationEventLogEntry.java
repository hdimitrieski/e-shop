package com.eshop.shared.outbox;

import com.eshop.shared.eventhandling.IntegrationEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IntegrationEventLogEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private UUID eventId;
  private LocalDateTime creationTime;
  private String eventTypeName;
  private String content;
  @Setter
  @Enumerated(EnumType.STRING)
  private EventState eventState;
  private Integer timesSent;
  private String topic;

  @Transient
  @Setter
  private IntegrationEvent event;

  IntegrationEventLogEntry(IntegrationEvent event, String content, String topic) throws JsonProcessingException {
    eventId = event.getId();
    creationTime = LocalDateTime.now();
    eventTypeName = event.getClass().getName();
    this.content = content;
    eventState = EventState.NotPublished;
    timesSent = 0;
    this.topic = topic;
  }

  void incrementTimesSent() {
    this.timesSent++;
  }

}
