import { RatingScale } from './rating';

export interface AddRatingEvent {
  catalogItemId: string;
  rating: RatingScale;
}
