import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class CatalogService {
  constructor(private http: HttpClient) {}

  fetchCatalogItems() {
    return this.http.get('/api/v1/catalog/items');
  }

  fetchCatalogTypes() {
    return this.http.get('/api/v1/catalog/catalogtypes');
  }

  fetchCatalogBrands() {
    return this.http.get('/api/v1/catalog/catalogbrands');
  }
}
