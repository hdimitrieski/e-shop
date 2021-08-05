import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { OrderStatusForm } from '../../admin/models';
import { v4 as uuid } from 'uuid';

@Injectable({providedIn: 'root'})
export class OrderManagementService {
  constructor(private readonly http: HttpClient) {
  }

  cancel(orderStatusForm: OrderStatusForm) {
    return this.http.put(`${environment.apiUrl}/api/v1/orders/cancel`, orderStatusForm, {
      headers: {
        'x-requestid': uuid()
      }
    });
  }

  ship(orderStatusForm: OrderStatusForm) {
    return this.http.put(`${environment.apiUrl}/api/v1/orders/ship`, orderStatusForm, {
      headers: {
        'x-requestid': uuid()
      }
    });
  }

}
