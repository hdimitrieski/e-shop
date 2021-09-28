package com.eshop.catalogquery.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Entity
@Table(name = "brand")
public class Brand extends DbEntity {
    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
