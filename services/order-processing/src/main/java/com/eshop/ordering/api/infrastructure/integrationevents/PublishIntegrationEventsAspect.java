package com.eshop.ordering.api.infrastructure.integrationevents;

import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

/**
 * Add unique name to the current thread and use it to store all events that should be published when executing the
 * thread. After the transaction is committed, publish all events that are stored for the current thread.
 *
 * Note: This is not used anymore because now the event publishing is handled by leveraging the outbox pattern.
 */
@AllArgsConstructor
//@Aspect
//@Configuration
class PublishIntegrationEventsAspect extends TransactionSynchronizationAdapter {
//  private final IntegrationEventLogService integrationEventLogService;

  @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
  public void registerTransactionSyncrhonization() {
    Thread.currentThread().setName("tx=" + UUID.randomUUID());
    TransactionSynchronizationManager.registerSynchronization(this);
  }

  @Override
  public void beforeCompletion() {
    super.beforeCompletion();
  }

  @Override
  public void afterCompletion(int status) {
    super.afterCompletion(status);
  }

  @Override
  public void beforeCommit(boolean readOnly) {
    super.beforeCommit(readOnly);
  }

  @Override
  public void afterCommit() {
    super.afterCommit();
//    integrationEventLogService.publishEventsThroughEventBus(Thread.currentThread().getName());
  }

}
