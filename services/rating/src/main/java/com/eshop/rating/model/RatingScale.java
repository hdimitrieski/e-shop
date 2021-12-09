package com.eshop.rating.model;

import lombok.Getter;

@Getter
public enum RatingScale {
  BAD(1),
  DECENT(2),
  GOOD(3),
  VERY_GOOD(4),
  EXCELLENT(5);

  private final Integer scale;

  RatingScale(int scale) {
    this.scale = scale;
  }
}
