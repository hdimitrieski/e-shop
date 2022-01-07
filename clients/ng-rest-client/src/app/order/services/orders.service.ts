import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderSummary } from '../models';
import { environment } from '../../../environments/environment';

@Injectable()
export class OrdersService {
  constructor(private readonly http: HttpClient) {
  }

  fetchAll(): Observable<OrderSummary[]> {
    return this.http.get<OrderSummary[]>(`${environment.apiUrl}/api/v1/orders/summaries`);
  }

  fetchById(id: string): Observable<OrderSummary[]> {
    return this.http.get<OrderSummary[]>(`${environment.apiUrl}/api/v1/orders/${id}`);
  }

}
