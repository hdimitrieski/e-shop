import { RatingOption } from './rating';

export interface AddRatingEvent {
  catalogItemId: string;
  rating: RatingOption;
}
