package com.eshop.catalog.infrastructure.entities;

interface EntityConverter<E, D> {
  E toEntity(D domainObject);

  D fromEntity(E entity);
}
