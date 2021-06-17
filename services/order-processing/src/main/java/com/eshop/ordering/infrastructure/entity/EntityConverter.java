package com.eshop.ordering.infrastructure.entity;

import com.eshop.ordering.domain.base.Entity;

interface EntityConverter<E extends DbEntity, D extends Entity<?>> {
  E toEntity(D domainEntity);

  D fromEntity(E entity);
}
