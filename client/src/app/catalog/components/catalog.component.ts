import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BasketItem } from '../../models';
import { CatalogItem } from '../models';

@Component({
  selector: 'es-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent {
  @Input() catalogItems: CatalogItem[];
  @Output() addItemToCart = new EventEmitter<BasketItem>();
}
