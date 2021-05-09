import { Component, OnInit } from '@angular/core';
import { ChangeFilterEvent } from '../models/changeFilterEvent';
import { Page } from '../models/page';
import { CatalogService } from '../services/catalog.service';

@Component({
  selector: 'es-catalog-page',
  templateUrl: './catalog-page.component.html',
})
export class CatalogPageComponent implements OnInit {
  selectedBrandId: number;
  selectedTypeId: number;

  catalogPage: Page;

  constructor(private catalogService: CatalogService) {}

  ngOnInit() {
    this.catalogService.fetchCatalogItems().subscribe((page) => {
      this.catalogPage = page;
    });
  }

  getFormValues(filterValue: ChangeFilterEvent) {
    this.catalogService
      .fetchCatalogItems(filterValue.brand.id, filterValue.type.id)
      .subscribe((page) => {
        this.catalogPage = page;
      });
  }
}
