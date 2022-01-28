import { Component, OnInit } from '@angular/core';
import { EshopStompService } from './core/services/eshop-stomp.service';
import { NotificationsService } from './core/services/notifications.service';

@Component({
  selector: 'es-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
  constructor(
    private readonly eshopStompService: EshopStompService,
    private readonly notificationsService: NotificationsService
  ) {
  }

  ngOnInit(): void {
    this.eshopStompService.watchOrderPaid().subscribe((notification) =>
      this.notificationsService.show({
        text: `The payment for order ${notification.orderId} has been accepted. Status: ${notification.status}`
      })
    );

    this.eshopStompService.watchOrderStockConfirmed().subscribe((notification) =>
      this.notificationsService.show({
        text: `The status of your order ${notification.orderId} has been changed to: ${notification.status}`
      })
    );

    this.eshopStompService.watchOrderSubmitted().subscribe((notification) =>
      this.notificationsService.show({
        text: `Your order ${notification.orderId} has been submitted. Status: ${notification.status}`
      })
    );

    this.eshopStompService.watchOrderShipped().subscribe((notification) =>
      this.notificationsService.show({
        text: `Your order ${notification.orderId} has been shipped. Status: ${notification.status}`
      })
    );

    this.eshopStompService.watchOrderCancelled().subscribe((notification) =>
      this.notificationsService.show({
        text: `Your order ${notification.orderId} has been cancelled. Status: ${notification.status}`
      })
    );
  }

}
