import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { OrderSummary } from '../models';
import { OrdersService } from '../services/orders.service';

@Injectable()
export class OrdersResolver implements Resolve<OrderSummary[]> {

  constructor(private readonly ordersService: OrdersService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<OrderSummary[]> {
    return this.ordersService.fetchAll();
  }

}
