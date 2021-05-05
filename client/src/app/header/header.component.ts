import { Component, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { AuthenticationService } from "../core/services/authentication.service";

@Component({
  selector: 'es-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
  public loggedIn$ = this.authenticationService.isLoggedIn();
  private subscription = new Subscription();
  
  constructor(
    private readonly authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.subscription.add(this.authenticationService.tokenExpired$.subscribe(() => {
      this.authenticationService.refreshToken();
    }))
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public login() {
    this.authenticationService.login().subscribe(() => console.log('logged in..'));
  }

  public logout() {
    this.authenticationService.logout().subscribe(() => console.log('logged out..'));
  }
}