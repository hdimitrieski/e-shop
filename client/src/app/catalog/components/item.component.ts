import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CatalogItem } from '../models';
import { BasketItem } from '../../models';

@Component({
  selector: 'es-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css'],
})
export class ItemComponent {
  @Input() item: CatalogItem;
  @Output() addToCart = new EventEmitter<BasketItem>();

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
}
