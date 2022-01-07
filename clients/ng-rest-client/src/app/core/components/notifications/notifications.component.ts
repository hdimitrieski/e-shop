import { Component } from '@angular/core';
import { NotificationsService } from '../../services/notifications.service';

@Component({
  selector: 'es-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent {
  constructor(public readonly notificationsService: NotificationsService) {
  }
}
