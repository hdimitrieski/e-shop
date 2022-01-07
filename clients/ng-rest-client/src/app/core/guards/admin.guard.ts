import { Injectable } from '@angular/core';
import { CanLoad, Route, Router, UrlSegment } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanLoad {

  constructor(private readonly router: Router, private readonly authenticationService: AuthenticationService) {
  }

  canLoad(route: Route, segments: UrlSegment[]): boolean {
    return this.authenticationService.isAdmin();
  }

}
