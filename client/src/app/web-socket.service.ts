import { Injectable } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { stompConfig } from './stomp.config';

@Injectable()
export class WebSocketService {
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

}
