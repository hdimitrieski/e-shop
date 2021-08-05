import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { OrderStatusForm } from '../models';

@Injectable()
export class OrderManagementService {
  constructor(private readonly http: HttpClient) {
  }

  cancel(orderStatusForm: OrderStatusForm) {
    return this.http.put(`${environment.apiUrl}/api/v1/orders/cancel`, orderStatusForm);
  }

  ship(orderStatusForm: OrderStatusForm) {
    return this.http.put(`${environment.apiUrl}/api/v1/orders/ship`, orderStatusForm);
  }

}
