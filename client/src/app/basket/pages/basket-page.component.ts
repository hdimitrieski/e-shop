import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BasketService } from '../../core/services/basket.service';
import { Basket } from '../../models';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { take } from 'rxjs/operators';

@Component({
  templateUrl: './basket-page.component.html'
})
export class BasketPageComponent implements OnInit {
  basket: Basket;
  form: FormGroup = null;

  constructor(
    private readonly router: Router,
    private readonly fb: FormBuilder,
    private readonly basketService: BasketService
  ) {
  }

  ngOnInit() {
    this.basket = this.basketService.getCustomerBasket();
    this.form = this.createForm();
  }

  goToCatalog() {
    this.router.navigate(['/catalog']);
  }

  updateQuantity() {
    this.basketService.updateQuantities(this.form.value.quantities).pipe(
      take(1)
    ).subscribe((updatedBasket) => this.basket = updatedBasket);
  }

  goToCheckout() {
    this.router.navigate(['/order-draft']);
  }

  private createForm(): FormGroup {
    const quantityControls = this.basket.items.map(item =>
      this.fb.control(item.quantity, Validators.min(1))
    );
    return this.fb.group({
      quantities: this.fb.array(quantityControls)
    });
  }
}
