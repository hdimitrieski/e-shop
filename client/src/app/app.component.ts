import { Component } from '@angular/core';
import { filter } from 'rxjs/operators';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'es-root',
  templateUrl: './app.component.html',
})
export class AppComponent {
  constructor(
    private oauthService: OAuthService
  ) {
    this.oauthService.events
      .pipe(filter(e => e.type === 'token_expires'))
      .subscribe(() => {
        this.oauthService.silentRefresh();
      });
  }

}
