import { Component, OnInit } from '@angular/core';
import { CatalogService } from '../../core/services/catalog.service';
import { BasketService } from '../../core/services/basket.service';
import { CatalogItem, CatalogPage, ChangeFilterEvent } from '../models';
import { BasketItem } from '../../models';
import { ActivatedRoute, Router } from '@angular/router';
import { distinctUntilChanged, mergeMap, take, tap } from 'rxjs/operators';
import { forkJoin } from "rxjs";
import { RatingService } from "../../core/services/rating.service";
import { Rating } from "../models/rating";

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
    private readonly route: ActivatedRoute,
    private ratingService: RatingService
  ) {
  }

  ngOnInit(): void {
    this.catalogService.fetchTopFive().subscribe(topFiveCatalogItems => {
      this.topFiveCatalogItems = topFiveCatalogItems;
    });

    this.route.queryParams.pipe(
      distinctUntilChanged(),
    ).subscribe(({brand, type, page}) => {
      this.catalogService
        .fetchCatalogItems(brand, type, page).pipe(
        tap((catalogPage: CatalogPage) => this.catalogPage = catalogPage),
        mergeMap((catalogPage: CatalogPage) => {
          return forkJoin(catalogPage.content.map(catalogItem => this.ratingService.fetchRatingsForCatalogItem(catalogItem.id)))
        })
      ).subscribe((ratings: Rating[][]) => {
        this.addRatingsToCatalogItems(ratings);
      });
    });
  }

  private addRatingsToCatalogItems(ratings: Rating[][]) {
    ratings.filter((arr) => arr.length !== 0).forEach((ratingsForCatalogItem: Rating[]) => {
      const catalogItemId = ratingsForCatalogItem[0].catalogItemId;
      const catalogItemIndex = this.catalogPage.content.findIndex((catalogItem: CatalogItem) => catalogItem.id === catalogItemId);
      this.catalogPage = {
        ...this.catalogPage,
        content: [
          ...this.catalogPage.content,
          this.catalogPage.content[catalogItemIndex] = {
            ...this.catalogPage.content[catalogItemIndex],
            ratings: ratingsForCatalogItem
          }
        ]
      };
    });
  }

  onFilterSubmitted({brand, type}: ChangeFilterEvent): void {
    this.router.navigate([], {queryParams: {brand: brand.id, type: type.id}, queryParamsHandling: 'merge'});
  }

  onPageChanged(page: number): void {
    this.router.navigate([], {queryParams: {page}, queryParamsHandling: 'merge'});
  }

  onAddItemToCart(basketItem: BasketItem): void {
    this.basketService.addToBasket(basketItem).pipe(
      take(1)
    ).subscribe(() => {
    });
  }
}
