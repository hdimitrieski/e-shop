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
@Table(name = "brand")
public class Brand extends AbstractEntity {
    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
