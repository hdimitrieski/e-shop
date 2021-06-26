import { Component, OnInit } from '@angular/core';
import { OrderDraftService } from '../../core/services/order-draft.service';
import { OrderDraft } from '../../models';
import { CheckoutService } from '../services/checkout.service';
import { CheckoutForm } from '../models';
import { take } from 'rxjs/operators';
import { NotificationsService } from '../../core/services/notifications.service';
import { BasketService } from '../../core/services/basket.service';
import { Router } from '@angular/router';

@Component({
  templateUrl: './order-draft-page.component.html'
})
export class OrderDraftPageComponent implements OnInit {
  orderDraft: OrderDraft;

  constructor(
    private readonly orderService: OrderDraftService,
    private readonly checkoutService: CheckoutService,
    private readonly basketService: BasketService,
    private readonly notificationsService: NotificationsService,
    private readonly router: Router
  ) {
  }

  ngOnInit() {
    this.orderDraft = this.orderService.getDraft();
  }

  onCheckout(checkoutForm: CheckoutForm) {
    this.checkoutService.checkout(checkoutForm).pipe(
      take(1)
    ).subscribe(() => {
      this.sendOrderSubmittedNotification();
      this.deleteBasket();
      this.goToCatalog();
    });
  }

  private sendOrderSubmittedNotification() {
    this.notificationsService.show({
      text: 'Your order has been submitted.'
    });
  }

  private deleteBasket() {
    this.basketService.deleteBasket().pipe(
      take(1)
    ).subscribe();
  }

  private goToCatalog() {
    this.router.navigate(['/']);
  }

}
