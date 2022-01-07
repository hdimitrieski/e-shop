import { Injectable } from '@angular/core';
import { RxStompService, StompHeaders } from '@stomp/ng2-stompjs';
import { stompConfig } from '../../stomp.config';
import { AuthenticationService } from './authentication.service';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { OrderNotification } from '../models';

@Injectable({
  providedIn: 'root'
})
export class EshopStompService {
  connectionState$ = this.stompService.connectionState$;

  constructor(
    private readonly stompService: RxStompService,
    private readonly authService: AuthenticationService
  ) {
  }

  async connect(): Promise<void> {
    this.stompService.configure({
      ...stompConfig,
      connectHeaders: {
        ...this.headers(this.authService.accessToken())
      }
    });
    this.stompService.activate();
  }

  watchOrdersWaitingValidation(): Observable<OrderNotification> {
    return this.watch('order-waiting-validation');
  }

  watchOrderCancelled(): Observable<OrderNotification> {
    return this.watch('order-cancelled');
  }

  watchOrderPaid(): Observable<OrderNotification> {
    return this.watch('order-paid');
  }

  watchOrderShipped(): Observable<OrderNotification> {
    return this.watch('order-shipped');
  }

  watchOrderStockConfirmed(): Observable<OrderNotification> {
    return this.watch('order-stock-confirmed');
  }

  watchOrderSubmitted(): Observable<OrderNotification> {
    return this.watch('order-submitted');
  }

  private watch(queueName: string): Observable<OrderNotification> {
    return this.stompService.watch(
      `/user/queue/${queueName}`,
      this.headers(this.authService.accessToken())
    ).pipe(
      map(message => JSON.parse(message.body))
    );
  }

  private headers(token: string): StompHeaders {
    return {
      Authorization: `bearer ${token}`
    };
  }

}
