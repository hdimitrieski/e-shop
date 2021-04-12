import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { InjectableRxStompConfig, RxStompService, rxStompServiceFactory } from '@stomp/ng2-stompjs';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { stompConfig } from './stomp.config';
import { WebSocketService } from './web-socket.service';

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8090/auth',
        realm: 'e-shop',
        clientId: 'eshop-client',
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      },
      bearerExcludedUrls: ['/assets'],
      loadUserProfileAtStartUp: true
    });
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    KeycloakAngularModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
    RxStompService,
    WebSocketService
    // {
    //   provide: InjectableRxStompConfig,
    //   useValue: stompConfig
    // },
    // {
    //   provide: RxStompService,
    //   useFactory: rxStompServiceFactory,
    //   deps: [InjectableRxStompConfig]
    // }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
