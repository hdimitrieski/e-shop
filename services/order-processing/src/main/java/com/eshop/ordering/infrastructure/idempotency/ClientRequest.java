package com.eshop.ordering.infrastructure.idempotency;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientRequest(UUID id, String name, LocalDateTime time) {
}
