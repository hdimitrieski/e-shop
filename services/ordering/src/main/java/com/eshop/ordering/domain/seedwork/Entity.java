package com.eshop.ordering.domain.seedwork;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class Entity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  protected Long id;

  public boolean isTransient() {
    return id == null;
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
