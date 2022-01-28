import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { OrderDraft } from '../../models';
import { Observable } from 'rxjs';
import { OrderDraftService } from '../../core/services/order-draft.service';
import { Injectable } from '@angular/core';
import { BasketService } from '../../core/services/basket.service';

@Injectable()
export class OrderDraftResolver implements Resolve<OrderDraft> {

  constructor(
    private readonly basketService: BasketService,
    private readonly orderService: OrderDraftService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<OrderDraft> | OrderDraft {
    return this.orderService.hasDraft()
      ? this.orderService.getDraft()
      : this.orderService.createDraft(this.basketService.getCustomerBasket());
  }

}
