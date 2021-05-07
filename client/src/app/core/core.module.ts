import { APP_INITIALIZER, NgModule } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { EshopStompService } from './services/eshop-stomp.service';
import { OAuthModule, OAuthService } from 'angular-oauth2-oidc';
import { HttpClientModule } from '@angular/common/http';
import { authCodeFlowConfig } from './services/auth-code-flow.config';

function initializeOAuth(oAuthService: OAuthService) {
  return () => {
    oAuthService.configure(authCodeFlowConfig);

    return oAuthService.loadDiscoveryDocumentAndTryLogin().then(() => {
      if (oAuthService.hasValidAccessToken()) {
        oAuthService.setupAutomaticSilentRefresh();
        return oAuthService.loadUserProfile()
      }
    });
  }
}

@NgModule({
  imports: [
    HttpClientModule,
    OAuthModule.forRoot({
      resourceServer: {
        allowedUrls: ['http://localhost:3000/api'],
        sendAccessToken: true
      }
    })
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeOAuth,
      multi: true,
      deps: [OAuthService],
    },
    RxStompService,
    EshopStompService
  ]
})
export class CoreModule {
}
