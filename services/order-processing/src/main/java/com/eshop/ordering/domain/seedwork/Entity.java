package com.eshop.ordering.domain.seedwork;

import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class Entity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
  @SequenceGenerator(name = "sequence_generator", sequenceName = "eshop_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false)
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
