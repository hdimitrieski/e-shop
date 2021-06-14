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
@Table(name = "catalog_brand")
public class CatalogBrand extends AbstractEntity {
    @Column(name = "brand", nullable = false, length = 100)
    private String brand;
}
