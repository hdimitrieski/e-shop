import { Component } from '@angular/core';
import { CatalogService } from '../../core/services/catalog.service';
import { CatalogItem } from '../../catalog/models';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationsService } from '../../core/services/notifications.service';

@Component({
  templateUrl: './catalog-item-form-page.component.html'
})
export class CatalogItemFormPageComponent {
  categories$ = this.catalogService.fetchCatalogTypes();
  brands$ = this.catalogService.fetchCatalogBrands();
  catalogItem$: Observable<CatalogItem> = this.route.data.pipe(map(data => data.catalogItem));

  constructor(
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly catalogService: CatalogService,
    private readonly notificationsService: NotificationsService
  ) {
  }

  addCatalogItem(catalogItem: CatalogItem): void {
    this.catalogService.add(catalogItem).subscribe(() => {
      this.notificationsService.show({text: 'Catalog item added'});
      this.router.navigate(['/admin']);
    });
  }

}
