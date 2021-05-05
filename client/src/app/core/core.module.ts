import { APP_INITIALIZER, NgModule } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { EshopStompService } from './services/eshop-stomp.service';

function initializeKeycloak(keycloak: KeycloakService) {
  // TODO HD move in env
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8090/auth',
        realm: 'e-shop',
        clientId: 'eshop-client',
      },
      initOptions: {
        enableLogging: true,
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html',
      },
      bearerExcludedUrls: ['/assets'],
      loadUserProfileAtStartUp: true,
      enableBearerInterceptor: true
    });
}

@NgModule({
  imports: [
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
    EshopStompService
  ]
})
export class CoreModule {
}
