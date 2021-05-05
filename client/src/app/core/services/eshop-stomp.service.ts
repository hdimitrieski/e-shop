import { Injectable } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { stompConfig } from '../../stomp.config';

@Injectable({
  providedIn: 'root'
})
export class EshopStompService {
  connectionState$ = this.stompService.connectionState$;

  constructor(private stompService: RxStompService) {
  }

  async connect(token: string) {
    this.stompService.configure({
      ...stompConfig,
      connectHeaders: {
        ...this.headers(token)
      }
    });
    this.stompService.activate();
  }

  watch(token: string, userName: string, queueName: string) {
    return this.stompService.watch(`/user/queue/${queueName}`, this.headers(token));
  }

  private headers(token: string) {
    return {
      Authorization: `bearer ${token}`
    };
  }

  private subscribeToQueues() {
    const token = "";
    const userProfile: any = {};

    this.watch(token, userProfile.username, 'order-waiting-validation').subscribe(message => {
      console.log('Waiting for validation: ', message.body);
    });

    this.watch(token, userProfile.username, 'order-cancelled').subscribe(message => {
      console.log('Cancelled: ', message.body);
    });

    this.watch(token, userProfile.username, 'order-paid').subscribe(message => {
      console.log('Paid: ', message.body);
    });

    this.watch(token, userProfile.username, 'order-shipped').subscribe(message => {
      console.log('Shipped: ', message.body);
    });

    this.watch(token, userProfile.username, 'order-stock-confirmed').subscribe(message => {
      console.log('Stock confirmed: ', message.body);
    });

    this.watch(token, userProfile.username, 'order-submitted').subscribe(message => {
      console.log('Submitted: ', message.body);
    });
  }

}
