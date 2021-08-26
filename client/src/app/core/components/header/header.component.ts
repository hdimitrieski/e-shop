import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../../services/authentication.service';
import { UserInfo } from 'angular-oauth2-oidc';
import { BasketService } from '../../services/basket.service';

@Component({
  selector: 'es-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit, OnDestroy {
  public loggedIn$ = this.authenticationService.isLoggedIn();
  public user: UserInfo = null;
  private subscription = new Subscription();

  constructor(
    private readonly authenticationService: AuthenticationService,
    private readonly basketService: BasketService
  ) {
  }

  ngOnInit(): void {
    if (this.authenticationService.isLoggedIn()) {
      this.subscription.add(
        this.authenticationService.loadUserProfile().subscribe(userInfo => {
          this.user = userInfo;
        })
      );
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public numberOfItems(): number {
    return this.basketService.getCustomerBasket()?.items.length;
  }

  public totalPrice(): number {
    return this.basketService.getCustomerBasket().items
      .map(item => item.quantity * item.unitPrice)
      .reduce((price, sum) => price + sum, 0);
  }

  public isAdmin(): boolean {
    return this.authenticationService.isAdmin();
  }

  public login(): void {
    this.authenticationService.login();
  }

  public logout(): void {
    this.authenticationService.logout();
  }
}
