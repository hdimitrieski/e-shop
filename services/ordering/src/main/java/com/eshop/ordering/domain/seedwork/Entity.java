package com.eshop.ordering.domain.seedwork;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public abstract class Entity {
    private int requestedHashCode;

    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    protected Integer id;

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
