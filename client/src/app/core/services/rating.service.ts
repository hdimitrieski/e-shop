import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  constructor(private readonly http: HttpClient) {
  }

  fetchRatingsForCatalogItem(catalogItemId: string) {
    return this.http.get<any>(`${environment.apiUrl}/api/v1/rating/${catalogItemId}`);
  }
}
