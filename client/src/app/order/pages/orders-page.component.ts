import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { OrderSummary } from '../models';
import { map } from 'rxjs/operators';

@Component({
  templateUrl: './orders-page.component.html'
})
export class OrdersPageComponent {
  orders$: Observable<OrderSummary[]> = this.route.data.pipe(map(data => data.orders));

  constructor(private readonly route: ActivatedRoute) {
  }
}
