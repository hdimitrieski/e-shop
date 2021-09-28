package com.eshop.catalog.domain.base;

import lombok.Getter;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.UUID;

public abstract class Entity {

  @AggregateIdentifier
  @Getter
  protected UUID id;

  public boolean isTransient() {
    return id == null;
  }

  protected void checkRule(@NonNull BusinessRule rule) {
    Objects.requireNonNull(rule, "business rule must not be null");

    if (rule.broken()) {
      throw new BusinessRuleBrokenException(rule);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Entity entity = (Entity) o;

    if (this.isTransient() || entity.isTransient()) return false;
    return id.equals(entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
