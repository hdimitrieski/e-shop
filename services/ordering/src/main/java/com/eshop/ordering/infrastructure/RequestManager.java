package com.eshop.ordering.infrastructure;

import java.util.UUID;

public interface RequestManager {
  boolean exist(UUID id);
  boolean createRequestForCommand(UUID id);
}
