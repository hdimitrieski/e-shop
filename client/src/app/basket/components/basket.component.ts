import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { BasketService } from '../../core/services/basket.service';
import { Basket } from '../models/basket';

@Component({
  selector: 'es-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css'],
})
export class BasketComponent implements OnInit {
  @ViewChild('quantity') quantityInput: ElementRef;
  basket: Basket;

  constructor(private router: Router, private basketService: BasketService) {}

  ngOnInit() {
    console.log('init');
    this.basket = this.basketService.getCustomerBasket();
  }

  goToCatalog() {
    this.router.navigate(['/catalog']);
  }

  updateQuantity() {
    console.log(this.quantityInput.nativeElement.value);
  }
}
