package com.eshop.ordering.domain.base;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Base class for value objects that are used as identifiers.
 */
public abstract class Identifier extends ValueObject {
  private final UUID uuid;

  protected Identifier(@NonNull UUID uuid) {
    this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
  }

  @NonNull
  public String getUuid() {
    return uuid.toString();
  }

  @NonNull
  public UUID getValue() {
    return uuid;
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(uuid);
  }
}
