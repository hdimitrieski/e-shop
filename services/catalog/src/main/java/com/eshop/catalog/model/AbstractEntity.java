package com.eshop.catalog.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * Base class to derive entity classes from.
 *
 * @author Oliver Gierke
 */
@MappedSuperclass
@Getter
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", sequenceName = "catalog_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) obj;

        return this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
