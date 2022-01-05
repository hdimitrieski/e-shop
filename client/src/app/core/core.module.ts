import { APP_INITIALIZER, NgModule } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { EshopStompService } from './services/eshop-stomp.service';
import { OAuthModule, OAuthService } from 'angular-oauth2-oidc';
import { HttpClientModule } from '@angular/common/http';
import { authCodeFlowConfig } from './services/auth-code-flow.config';
import { BasketService } from './services/basket.service';
import { OrderDraftService } from './services/order-draft.service';
import { NgbDateAdapter, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DateAdapter } from './adapters/date.adapter';
import { NotificationsComponent } from './components/notifications/notifications.component';
import { BrowserModule } from '@angular/platform-browser';
import { HeaderComponent } from './components/header/header.component';
import { RouterModule } from '@angular/router';
import { environment } from '../../environments/environment';
import {CustomerBasket} from '../models';

function initializeOAuth(
  oAuthService: OAuthService,
  basketService: BasketService,
  eshopStompService: EshopStompService
): () => Promise<CustomerBasket> {
  return () => {
    oAuthService.configure(authCodeFlowConfig);
    oAuthService.setupAutomaticSilentRefresh();

    return oAuthService.loadDiscoveryDocumentAndTryLogin().then(() => {
      if (oAuthService.hasValidAccessToken()) {
        eshopStompService.connect();
        return oAuthService.loadUserProfile().then((user) => {
          return basketService.getBasket(user.preferred_username).toPromise();
        });
      }
    });
  };
}

const apiGatewayUrls = ['/api/v1/basket', '/api/v1/orders', '/api/v1/catalog', '/api/v1/rating']
  .map(path => `${environment.apiUrl}${path}`);

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    OAuthModule.forRoot({
      resourceServer: {
        allowedUrls: [...apiGatewayUrls, `${environment.imageApiUrl}/images/upload`],
        sendAccessToken: true
      }
    }),
    NgbModule,
    RouterModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeOAuth,
      multi: true,
      deps: [OAuthService, BasketService, EshopStompService],
    },
    {
      provide: NgbDateAdapter,
      useClass: DateAdapter
    },
    RxStompService,
    EshopStompService,
    BasketService,
    OrderDraftService
  ],
  declarations: [
    HeaderComponent,
    NotificationsComponent
  ],
  exports: [
    HeaderComponent,
    NotificationsComponent
  ]
})
export class CoreModule {
}
