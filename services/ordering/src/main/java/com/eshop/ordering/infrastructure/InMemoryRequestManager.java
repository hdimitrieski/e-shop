package com.eshop.ordering.infrastructure;

import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InMemoryRequestManager implements RequestManager {

  private final Map<UUID, ClientRequest> requestsById = new HashMap<>();

  @Override
  public boolean exist(UUID id) {
    return requestsById.containsKey(id);
  }

  @Override
  public void createRequestForCommand(UUID id, String commandName) {
    if (exist(id)) {
      throw new OrderingDomainException("Request with id: %s already exists".formatted(id));
    }

    requestsById.put(id, new ClientRequest(id, "", LocalDateTime.now()));
  }
}
