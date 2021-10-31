import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CatalogItem } from '../models';
import { BasketItem } from '../../models';
import { RatingOption } from '../models/rating';

@Component({
  selector: 'es-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css'],
})
export class ItemComponent {
  @Input() item: CatalogItem;
  @Output() addToCart = new EventEmitter<BasketItem>();

  totalRating = 0;

  RatingOption = RatingOption;

  ngOnChanges() {
    if (this.item.ratings?.length !== 0) {
      this.totalRating = this.calculateRating();
    }
  }

  onAddToCart(): void {
    this.addToCart.emit({
      id: this.item.id,
      productId: this.item.id,
      productName: this.item.name,
      unitPrice: this.item.price,
      oldUnitPrice: this.item.price,
      quantity: 1,
      pictureUrl: this.item.pictureFileName
    });
  }

  calculateRating() {
    let sum = 0;
    if (this.item.ratings && this.item.ratings.length !== 0) {
      this.item.ratings.map(rating => sum += this.transformRatingOption(rating.rating));
      return sum / this.item.ratings.length;
    }
    return sum;
  }

  transformRatingOption(ratingOption: RatingOption) {
    switch (ratingOption) {
      case RatingOption.EXCELLENT:
        return 5;
      case RatingOption.VERY_GOOD:
        return 4;
      case RatingOption.GOOD:
        return 3;
      case RatingOption.DECENT:
        return 2;
      case RatingOption.BAD:
        return 1;
    }
  }

  onAddRating(event: any) {
    event.stopPropagation();
  }

}
