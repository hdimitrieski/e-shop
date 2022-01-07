import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { AddRatingEvent } from '../../catalog/models/add-rating-event';
import { Rating } from '../../catalog/models/rating';

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  constructor(private readonly http: HttpClient) {
  }

  fetchRatingsForCatalogItem(catalogItemId: string) {
    return this.http.get<any>(`${environment.apiUrl}/api/v1/rating/${catalogItemId}`);
  }

  addRatingToCatalogItem({catalogItemId, rating}: AddRatingEvent) {
    return this.http.post<Rating>(`${environment.apiUrl}/api/v1/rating/${catalogItemId}`, {catalogItemId, rating});
  }
}
