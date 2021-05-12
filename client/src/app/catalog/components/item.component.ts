import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Basket } from 'src/app/basket/models/basket';
import { BasketService } from 'src/app/basket/services/basket.service';
import { CatalogItem } from '../models/catalogItem';

@Component({
  selector: 'es-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css'],
})
export class ItemComponent {
  @Input() item: CatalogItem;

  constructor(private basketService: BasketService, private router: Router) {}

  addToCart() {
    let basket: Basket = {
      items: [
        {
          id: this.item.id,
          productId: this.item.id,
          productName: this.item.name,
          unitPrice: this.item.price,
          oldUnitPrice: this.item.price,
          quantity: 1,
          pictureUrl: this.item.pictureFileName,
        },
      ],
    };

    this.basketService.updateBasket(basket).subscribe((item) => {
      this.router.navigate(['/basket']);
    });
  }
}
