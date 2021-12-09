import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CatalogItem } from '../models';
import { BasketItem } from '../../models';
import { RatingScale } from '../models/rating';
import { AddRatingEvent } from '../models/add-rating-event';

@Component({
  selector: 'es-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css'],
})
export class ItemComponent {
  @Input() item: CatalogItem;
  @Output() addToCart = new EventEmitter<BasketItem>();
  @Output() addRating = new EventEmitter<AddRatingEvent>();
  @Input() isTopFive: boolean = false;

  RatingScale = RatingScale;

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

  onAddRating({target: {value}}) {
    this.addRating.emit({catalogItemId: this.item.id, rating: value});
  }

}
