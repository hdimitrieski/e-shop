import { Component } from '@angular/core';
import { OrderManagementService } from '../../core/services/order-management.service';
import { OrderStatusForm } from '../models';
import { NotificationsService } from '../../core/services/notifications.service';
import { take } from 'rxjs/operators';

@Component({
  templateUrl: './order-management-page.component.html'
})
export class OrderManagementPageComponent {
  constructor(
    private readonly notificationsService: NotificationsService,
    private readonly orderManagementService: OrderManagementService
  ) {
  }

  public cancelOrder(orderStatusForm: OrderStatusForm): void {
    this.orderManagementService.cancel(orderStatusForm).pipe(
      take(1)
    ).subscribe(() => this.notificationsService.show({
      text: `Order cancellation has been requested.`
    }));
  }

  public shipOrder(orderStatusForm: OrderStatusForm): void {
    this.orderManagementService.ship(orderStatusForm).pipe(
      take(1)
    ).subscribe(() => this.notificationsService.show({
      text: `Order shipment has been requested.`
    }));
  }

}
