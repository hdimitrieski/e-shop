export enum RatingOption {
  BAD = 'BAD',
  DECENT = 'DECENT',
  GOOD = 'GOOD',
  VERY_GOOD = 'VERY_GOOD',
  EXCELLENT = 'EXCELLENT'
}

export interface Rating {
  ratingOption: RatingOption;
  catalogItemId: string;
}
