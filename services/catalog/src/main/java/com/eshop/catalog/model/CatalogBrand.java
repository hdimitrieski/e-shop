package com.eshop.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CatalogBrand extends AbstractEntity {
    @Column(nullable = false, length = 100)
    private String brand;
}
