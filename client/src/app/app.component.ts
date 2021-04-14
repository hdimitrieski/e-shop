import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import { WebSocketService } from './web-socket.service';

@Component({
  selector: 'es-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
  public isLoggedIn = false;
  public userProfile: KeycloakProfile | null = null;
  private token: string = null;

  constructor(
    private readonly keycloak: KeycloakService,
    private readonly webSocketService: WebSocketService
  ) {
  }

  public async ngOnInit() {
    this.isLoggedIn = await this.keycloak.isLoggedIn();

    if (this.isLoggedIn) {
      this.userProfile = await this.keycloak.loadUserProfile();
    }
    this.token = await this.keycloak.getToken();

    await this.webSocketService.connect(this.token);
    this.webSocketService.connectionState$.subscribe(console.log);

    if (this.token) {
      this.subscribeToQueues();
    }
  }

  public login() {
    this.keycloak.login();
  }

  public logout() {
    this.keycloak.logout();
  }

  private subscribeToQueues() {
    this.webSocketService.watch(this.token, this.userProfile.username, 'order-waiting-validation').subscribe(message => {
      console.log('Waiting for validation: ', message.body);
    });

    this.webSocketService.watch(this.token, this.userProfile.username, 'order-cancelled').subscribe(message => {
      console.log('Cancelled: ', message.body);
    });

    this.webSocketService.watch(this.token, this.userProfile.username, 'order-paid').subscribe(message => {
      console.log('Paid: ', message.body);
    });

    this.webSocketService.watch(this.token, this.userProfile.username, 'order-shipped').subscribe(message => {
      console.log('Shipped: ', message.body);
    });

    this.webSocketService.watch(this.token, this.userProfile.username, 'order-stock-confirmed').subscribe(message => {
      console.log('Stock confirmed: ', message.body);
    });

    this.webSocketService.watch(this.token, this.userProfile.username, 'order-submitted').subscribe(message => {
      console.log('Submitted: ', message.body);
    });
  }
}
