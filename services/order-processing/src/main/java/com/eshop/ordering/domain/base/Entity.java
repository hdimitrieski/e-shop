package com.eshop.ordering.domain.base;

import com.eshop.ordering.domain.exceptions.BusinessRuleBrokenException;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Objects;

public abstract class Entity<ID extends Identifier> {

  @Getter
  protected ID id;

  public boolean isTransient() {
    return id == null;
  }

  @NonNull
  public abstract Snapshot snapshot();

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
    Entity<ID> entity = (Entity<ID>) o;

    if (this.isTransient() || entity.isTransient()) return false;
    return id.equals(entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
