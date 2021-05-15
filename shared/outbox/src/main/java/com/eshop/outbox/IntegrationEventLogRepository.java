package com.eshop.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IntegrationEventLogRepository extends JpaRepository<IntegrationEventLogEntry, Long> {
  Optional<IntegrationEventLogEntry> findByEventId(UUID eventId);
}
