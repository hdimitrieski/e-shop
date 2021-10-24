package com.eshop.ordering.api.infrastructure.requestmanager;

import java.util.UUID;

/**
 * Used for handling command idempotency.
 */
public interface RequestManager {
  boolean exist(UUID id);

  void createRequestForCommand(UUID id, String commandName);
}
