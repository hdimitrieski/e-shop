import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../core/services/authentication.service';

@Component({
  selector: 'es-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit, OnDestroy {
  public loggedIn$ = this.authenticationService.isLoggedIn();
  private subscription = new Subscription();
  public user;

  constructor(private readonly authenticationService: AuthenticationService,
    private router: Router) {}

  ngOnInit(): void {
    if (this.authenticationService.isLoggedIn()) {
      this.authenticationService.loadUserProfile().subscribe(userInfo => {
        this.user = userInfo;
      })
    }
    
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public login() {
    this.authenticationService.login();
  }

  public logout() {
    this.authenticationService.logout();
  }

  goToBasket() {
    this.router.navigate(['/basket']);
  }
}
