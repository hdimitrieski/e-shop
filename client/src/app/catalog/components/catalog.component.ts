import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BasketItem } from '../../models';
import { CatalogItem } from '../models';
import { AddRatingEvent } from '../models/add-rating-event';

@Component({
  selector: 'es-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent {
  @Input() catalogItems: CatalogItem[];
  @Output() addItemToCart = new EventEmitter<BasketItem>();
  @Output() addRating = new EventEmitter<AddRatingEvent>();
}
