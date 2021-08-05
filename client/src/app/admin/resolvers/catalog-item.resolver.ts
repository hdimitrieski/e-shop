import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { CatalogService } from '../../core/services/catalog.service';
import { CatalogItem } from '../../catalog/models';

@Injectable()
export class CatalogItemResolver implements Resolve<CatalogItem> {

  constructor(private readonly catalogService: CatalogService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CatalogItem> {
    return route.params.catalogItemId
      ? this.catalogService.fetchById(route.params.catalogItemId)
      : null;
  }

}
