import { Component, OnInit } from '@angular/core';
import { CatalogService } from '../../core/services/catalog.service';
import { CatalogItem, CatalogPage } from '../../catalog/models';
import { take } from 'rxjs/operators';
import { NotificationsService } from '../../core/services/notifications.service';
import { Validators } from '@angular/forms';
import { ValueChanged } from '../models/value-changed';

@Component({
  templateUrl: './catalog-management.page.component.html'
})
export class CatalogManagementPageComponent implements OnInit {
  public catalogPage: CatalogPage = null;
  public nameValidators = [
    Validators.required,
    Validators.minLength(5),
    Validators.maxLength(50)
  ];
  public requiredValidator = [
    Validators.required
  ];

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

  public nameChanged(updatedName: ValueChanged<string>) {
    this.catalogService.updateName(updatedName)
      .pipe(take(1))
      .subscribe(() => {
        const updatedItem = this.catalogPage.content.find(c => c.id === updatedName.productId);
        updatedItem.name = updatedName.value;
        this.notificationsService.show({text: `Catalog item name updated.`});
      });
  }

  public priceChanged(updatedPrice: ValueChanged<number>) {
    this.catalogService.updatePrice(updatedPrice)
      .pipe(take(1))
      .subscribe(() => {
        const updatedItem = this.catalogPage.content.find(c => c.id === updatedPrice.productId);
        updatedItem.price = updatedPrice.value;
        this.notificationsService.show({text: `Catalog item price updated.`});
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
