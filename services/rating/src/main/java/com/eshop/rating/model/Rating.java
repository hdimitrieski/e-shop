package com.eshop.rating.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "rating")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
public class Rating {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "catalog_item_id")
  private UUID catalogItemId;

  @Column
  private RatingScale rating;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
      return false;
    }

    Rating that = (Rating) obj;

    return this.id.equals(that.getId());
  }

  @Override
  public int hashCode() {
    return id == null ? 0 : id.hashCode();
  }

}
