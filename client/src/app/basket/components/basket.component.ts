import { Component, OnInit } from '@angular/core';
import { switchMap, take } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { Basket } from '../models/basket';
import { BasketService } from '../services/basket.service';

@Component({
  selector: 'es-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css'],
})
export class BasketComponent implements OnInit {
  basket: Basket;

  constructor(
    private basketService: BasketService,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit() {
    this.authenticationService
      .loadUserProfile()
      .pipe(
        switchMap((user) => {
          return this.basketService.getBasket(user.preferred_username);
        }),
        take(1)
      )
      .subscribe((basket) => {
        this.basket = basket;
        console.log(this.basket);
      });
  }
}
