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

  onPreviousClick() {
    let currentPageIndex: number = this.catalogPage.pageable.page;
    if (currentPageIndex > 0) {
      this.catalogService
      .fetchCatalogItems(this.selectedBrandId, this.selectedTypeId, currentPageIndex - 1)
      .subscribe((page) => {
        this.catalogPage = page;
      })
    }
  }

  onNextClick() {
    let currentPageIndex: number = this.catalogPage.pageable.page;
    console.log(currentPageIndex);
    if (currentPageIndex + 1 < this.catalogPage.total) {
      this.catalogService
      .fetchCatalogItems(this.selectedBrandId, this.selectedTypeId, this.catalogPage.pageable.page + 1)
      .subscribe((page) => {
        this.catalogPage = page;
      })
    }
  }
}
