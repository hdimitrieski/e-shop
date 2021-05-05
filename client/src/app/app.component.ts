import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthenticationService } from './core/services/authentication.service';
import { filter, switchMap } from 'rxjs/operators';
import { Subscription } from 'rxjs';

@Component({
  selector: 'es-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit, OnDestroy {
  public loggedIn$ = this.authenticationService.isLoggedIn();
  public userProfile$ = this.loggedIn$.pipe(
    filter(loggedIn => loggedIn),
    switchMap(() => this.authenticationService.loadUserProfile())
  );
  // TODO HD https://medium.com/scrum-and-coke/securing-angular-11-app-with-identityserver4-and-code-flow-pkce-5eddc68666b2
  // https://ordina-jworks.github.io/security/2019/08/22/Securing-Web-Applications-With-Keycloak.html
  // https://github.com/manfredsteyer/angular-oauth2-oidc
  private subscription = new Subscription();

  constructor(
    private readonly authenticationService: AuthenticationService
  ) {
  }

  ngOnInit(): void {
    this.subscription.add(this.authenticationService.tokenExpired$.subscribe(() => {
      this.authenticationService.refreshToken()
    }));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public login() {
    this.authenticationService.login()
      .subscribe(() => console.log('logged in...'));
  }

  public logout() {
    this.authenticationService.logout()
      .subscribe(() => console.log("Logged out"));
  }
}
