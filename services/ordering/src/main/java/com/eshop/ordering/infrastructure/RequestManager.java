package com.eshop.ordering.infrastructure;

import java.util.UUID;

/**
 * Used for handling idempotency.
 */
public interface RequestManager {
  boolean exist(UUID id);
  void createRequestForCommand(UUID id, String commandName);
}
