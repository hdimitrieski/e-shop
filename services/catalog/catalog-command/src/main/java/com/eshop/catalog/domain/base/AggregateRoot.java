package com.eshop.catalog.domain.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AggregateRoot extends Entity {

  protected AggregateRoot(UUID id) {
    checkNotNull(id);
    this.id = id;
  }

}
