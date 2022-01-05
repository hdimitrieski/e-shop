export enum RatingScale {
  BAD = 'BAD',
  DECENT = 'DECENT',
  GOOD = 'GOOD',
  VERY_GOOD = 'VERY_GOOD',
  EXCELLENT = 'EXCELLENT'
}

export interface Rating {
  ratingScale: RatingScale;
  catalogItemId: string;
}
