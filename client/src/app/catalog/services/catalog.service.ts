import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { toQueryParams } from 'src/app/utils/to-query-params';
import { CatalogBrand } from '../models/catalogBrand';
import { CatalogType } from '../models/catalogType';
import { Page } from '../models/page';

@Injectable()
export class CatalogService {
  constructor(private http: HttpClient) {}

  fetchCatalogItems(brandId?: number, typeId?: number) {
    return this.http.get('/api/v1/catalog/items', {
      params: toQueryParams({
        brandId,
        typeId,
      }),
    }) as Observable<Page>;
  }

  fetchCatalogTypes() {
    return this.http.get('/api/v1/catalog/catalogtypes') as Observable<
      CatalogType[]
    >;
  }

  fetchCatalogBrands() {
    return this.http.get('/api/v1/catalog/catalogbrands') as Observable<
      CatalogBrand[]
    >;
  }
}
