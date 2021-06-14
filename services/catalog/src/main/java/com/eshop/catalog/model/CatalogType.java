package com.eshop.catalog.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@Table(name = "catalog_type")
public class CatalogType extends AbstractEntity {
    @Column(name = "type", nullable = false, length = 100)
    private String type;
}
