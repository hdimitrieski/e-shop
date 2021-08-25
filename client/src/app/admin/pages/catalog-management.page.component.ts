import { Component, OnInit } from '@angular/core';
import { CatalogService } from '../../core/services/catalog.service';
import { CatalogItem, CatalogPage } from '../../catalog/models';
import { take } from 'rxjs/operators';
import { NotificationsService } from '../../core/services/notifications.service';

@Component({
  templateUrl: './catalog-management.page.component.html'
})
export class CatalogManagementPageComponent implements OnInit {
  public catalogPage: CatalogPage = null;

  constructor(
    private readonly catalogService: CatalogService,
    private readonly notificationsService: NotificationsService
  ) {
    this.catalogService.fetchCatalogItems();
  }

  ngOnInit(): void {
    this.fetchCatalogItems(0);
  }

  public onPageChanged(page: number): void {
    this.fetchCatalogItems(page);
  }

  public deleteCatalogItem(catalogItem: CatalogItem): void {
    this.catalogService.delete(catalogItem.id).subscribe(() => {
      this.notificationsService.show({text: `Catalog item ${catalogItem.id} deleted.`});
      this.catalogPage = {
        ...this.catalogPage,
        content: this.catalogPage.content.filter(c => c.id !== catalogItem.id)
      };
    });
  }

  private fetchCatalogItems(pageIndex: number): void {
    this.catalogService
      .fetchCatalogItems(null, null, pageIndex)
      .pipe(take(1))
      .subscribe((catalogPage) => {
        this.catalogPage = catalogPage;
      });
  }

}
