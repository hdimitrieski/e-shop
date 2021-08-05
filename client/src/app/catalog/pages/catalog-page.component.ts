import { Component, OnInit } from '@angular/core';
import { CatalogService } from '../../core/services/catalog.service';
import { BasketService } from '../../core/services/basket.service';
import { CatalogItem, CatalogPage, ChangeFilterEvent } from '../models';
import { BasketItem } from '../../models';
import { ActivatedRoute, Router } from '@angular/router';
import { distinctUntilChanged, take } from 'rxjs/operators';

@Component({
  templateUrl: './catalog-page.component.html',
})
export class CatalogPageComponent implements OnInit {
  catalogTypes$ = this.catalogService.fetchCatalogTypes();
  catalogBrands$ = this.catalogService.fetchCatalogBrands();
  catalogPage: CatalogPage = null;
  topFiveCatalogItems: CatalogItem[] = [];

  constructor(
    private readonly catalogService: CatalogService,
    private readonly basketService: BasketService,
    private readonly router: Router,
    private readonly route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.catalogService.fetchTopFive().subscribe(topFiveCatalogItems => {
      this.topFiveCatalogItems = topFiveCatalogItems;
    });

    this.route.queryParams.pipe(
      distinctUntilChanged(),
    ).subscribe(({brand, type, page}) => {
      this.catalogService
        .fetchCatalogItems(brand, type, page)
        .subscribe((page) => {
          this.catalogPage = page;
        });
    });
  }

  onFilterSubmitted({brand, type}: ChangeFilterEvent) {
    this.router.navigate([], {queryParams: {brand: brand.id, type: type.id}, queryParamsHandling: 'merge'});
  }

  onPageChanged(page: number) {
    this.router.navigate([], {queryParams: {page}, queryParamsHandling: 'merge'});
  }

  onAddItemToCart(basketItem: BasketItem) {
    this.basketService.addToBasket(basketItem).pipe(
      take(1)
    ).subscribe(() => console.info('Item added to basket'));
  }

}
