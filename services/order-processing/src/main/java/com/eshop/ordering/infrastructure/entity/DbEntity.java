package com.eshop.ordering.infrastructure.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@MappedSuperclass
abstract class DbEntity {
  @Id
  @Getter
  protected UUID id;
}
