import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Order } from '../models';
import { map } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';

@Component({
  templateUrl: './order-page.component.html'
})
export class OrderPageComponent {
  order$: Observable<Order> = this.route.data.pipe(map(data => data.order));

  constructor(private readonly route: ActivatedRoute) {
  }
}
