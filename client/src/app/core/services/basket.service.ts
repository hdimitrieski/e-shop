import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap, take } from 'rxjs/operators';
import { BasketItem } from 'src/app/basket/models/basketItem';
import { CustomerBasket } from '../../basket/models/customerBasket';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class BasketService {
  private customerBasket: CustomerBasket = {
    buyerId: null,
    items: [],
  };

  constructor(
    private http: HttpClient,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {
    if (this.authenticationService.isLoggedIn()) {
      this.authenticationService
        .loadUserProfile()
        .pipe(
          switchMap((user) => {
            return this.getBasket(user.preferred_username);
          }),
          take(1)
        )
        .subscribe((basket) => {
          this.customerBasket = basket;
          console.log(this.customerBasket);
        });
    }
  }

  getCustomerBasket() {
    return this.customerBasket;
  }

  addToBasket(catalogItem: BasketItem) {
    this.customerBasket.items.push(catalogItem);

    this.updateBasket(this.customerBasket).subscribe(() => {
      console.log('Test');
      this.router.navigate(['/basket']);
    });
  }

  private updateBasket(basketItems: CustomerBasket) {
    return this.http.post<CustomerBasket>(
      '/api/v1/basket',
      basketItems
    ) as Observable<CustomerBasket>;
  }

  private getBasket(customerId: string) {
    return this.http.get<CustomerBasket>(
      `/api/v1/basket/${customerId}`
    ) as Observable<CustomerBasket>;
  }
}
