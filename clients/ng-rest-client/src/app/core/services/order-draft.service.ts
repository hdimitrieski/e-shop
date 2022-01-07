import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerBasket, OrderDraft } from '../../models';
import { tap } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

@Injectable()
export class OrderDraftService {
  private orderDraft: OrderDraft = null;

  constructor(private readonly http: HttpClient) {
  }

  hasDraft(): boolean {
    return !!this.orderDraft;
  }

  getDraft(): OrderDraft {
    return this.orderDraft;
  }

  createDraft(customerBasket: CustomerBasket): Observable<OrderDraft> {
    return this.http.post<OrderDraft>(`${environment.apiUrl}/api/v1/orders/draft`, customerBasket).pipe(
      tap(draft => this.orderDraft = draft)
    );
  }
}
