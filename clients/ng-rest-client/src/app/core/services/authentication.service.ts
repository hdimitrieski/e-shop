import { Injectable } from '@angular/core';
import { OAuthEvent, OAuthService, UserInfo } from 'angular-oauth2-oidc';
import { fromPromise } from 'rxjs/internal-compatibility';
import { Observable } from 'rxjs';
import { filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public tokenExpired$ = this.oAuthService.events.pipe(
    filter(event => event.type === 'token_expires')
  );

  public tokenReceived$ = this.oAuthService.events.pipe(
    filter(event => event.type === 'token_received')
  );

  constructor(private readonly oAuthService: OAuthService) {
  }

  public refreshToken(): Observable<OAuthEvent> {
    return fromPromise(this.oAuthService.silentRefresh());
  }

  public accessToken(): string {
    return this.oAuthService.getAccessToken();
  }

  public loadUserProfile(): Observable<UserInfo> {
    return fromPromise(this.oAuthService.loadUserProfile());
  }

  public isLoggedIn(): boolean {
    return this.oAuthService.hasValidAccessToken();
  }

  public isAdmin(): boolean {
    return this.roles().some(role => role === 'ROLE_admin');
  }

  public login(): Observable<boolean> {
    return fromPromise(this.oAuthService.loadDiscoveryDocumentAndLogin());
  }

  public logout(): void {
    this.oAuthService.logOut();
  }

  private roles(): string[] {
    return this.isLoggedIn()
      ? (this.oAuthService.getIdentityClaims() as any).roles
      : [];
  }

}
