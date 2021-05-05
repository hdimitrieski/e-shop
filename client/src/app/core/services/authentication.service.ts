import { Injectable } from '@angular/core';
import { KeycloakEventType, KeycloakService } from 'keycloak-angular';
import { Observable } from 'rxjs';
import { fromPromise } from 'rxjs/internal-compatibility';
import { KeycloakProfile } from 'keycloak-js';
import { filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public tokenExpired$ = this.keycloak.keycloakEvents$.pipe(
    filter(event => event.type === KeycloakEventType.OnTokenExpired)
  );

  constructor(private readonly keycloak: KeycloakService) {
  }

  public refreshToken(): Observable<boolean> {
    return fromPromise(this.keycloak.updateToken(5));
  }

  public accessToken(): Observable<string> {
    return fromPromise(this.keycloak.getToken());
  }

  public loadUserProfile(): Observable<KeycloakProfile> {
    return fromPromise(this.keycloak.loadUserProfile());
  }

  public isLoggedIn(): Observable<boolean> {
    return fromPromise(this.keycloak.isLoggedIn());
  }

  public login(): Observable<void> {
    return fromPromise(this.keycloak.login());
  }

  public logout(): Observable<void> {
    return fromPromise(this.keycloak.logout());
  }

}
