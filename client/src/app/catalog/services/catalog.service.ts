import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CatalogBrand, CatalogItem, CatalogPage, CatalogType } from '../models';
import { toQueryParams } from '../../utils/to-query-params';

@Injectable()
export class CatalogService {
  constructor(private readonly http: HttpClient) {
  }

  fetchCatalogItems(brandId?: number, typeId?: number, pageIndex?: number): Observable<CatalogPage> {
    return this.http.get<CatalogPage>('/api/v1/catalog/items', {
      params: toQueryParams({
        brandId,
        typeId,
        pageIndex
      }),
    });
  }

  fetchCatalogTypes(): Observable<CatalogType[]> {
    return this.http.get<CatalogType[]>('/api/v1/catalog/catalogtypes');
  }

  fetchCatalogBrands(): Observable<CatalogBrand[]> {
    return this.http.get<CatalogBrand[]>('/api/v1/catalog/catalogbrands');
  }

  fetchTopFive(): Observable<CatalogItem[]> {
    return this.http.get<CatalogItem[]>('api/v1/catalog/topfive');
  }

}
