package com.eshop.shared.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableJpaRepositories
@EntityScan
public class OutboxConfiguration {

  @Bean
  public IntegrationEventLogService integrationEventLogService(
      IntegrationEventLogRepository integrationEventLogRepository
  ) {
    return new IntegrationEventLogServiceImpl(eventLogObjectMapper(), integrationEventLogRepository);
  }

  @Bean
  IntegrationEventProcessor integrationEventProcessor(
      IntegrationEventLogService integrationEventLogService,
      IntegrationEventPublisher integrationEventPublisher
  ) {
    return new IntegrationEventProcessor(integrationEventLogService, integrationEventPublisher);
  }

  private ObjectMapper eventLogObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }

}
