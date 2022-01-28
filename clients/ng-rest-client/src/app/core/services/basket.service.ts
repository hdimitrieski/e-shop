import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { BasketItem, CustomerBasket } from '../../models';
import { environment } from '../../../environments/environment';

@Injectable()
export class BasketService {
  private customerBasket: CustomerBasket = this.emptyBasket();

  constructor(private readonly http: HttpClient) {
  }

  getCustomerBasket(): CustomerBasket {
    return this.customerBasket;
  }

  addToBasket(catalogItem: BasketItem): Observable<CustomerBasket> {
    const updatedBasket = {
      ...this.customerBasket,
      items: [
        ...this.customerBasket.items,
        catalogItem
      ]
    };

    return this.updateBasket(updatedBasket);
  }

  getBasket(customerId: string): Observable<CustomerBasket> {
    return this.http.get<CustomerBasket>(`${environment.apiUrl}/api/v1/basket/customer/${customerId}`).pipe(
      tap(basket => this.customerBasket = basket),
      catchError(() => of(this.emptyBasket()))
    );
  }

  updateQuantities(quantities: number[]): Observable<CustomerBasket> {
    const quantitiesById = this.quantitiesById(quantities);

    const basketItemUpdates = {
      basketId: this.customerBasket.buyerId,
      updates: this.customerBasket.items
        .filter((item) => item.quantity !== quantitiesById[item.id])
        .map((item) => ({
          basketItemId: item.id,
          newQuantity: quantitiesById[item.id]
        }))
    };

    return this.http.put<CustomerBasket>(`${environment.apiUrl}/api/v1/basket/items`, basketItemUpdates).pipe(
      tap(basket => this.customerBasket = basket)
    );
  }

  private quantitiesById(quantities: number[]): {} {
    return this.customerBasket.items.reduce(
      ((byId, item, i) => ({
        ...byId,
        [item.id]: quantities[i]
      })),
      {}
    );
  }

  private updateBasket(basketItems: CustomerBasket): Observable<CustomerBasket> {
    return this.http.post<CustomerBasket>(`${environment.apiUrl}/api/v1/basket`, basketItems).pipe(
      tap(basket => this.customerBasket = basket)
    );
  }

  private emptyBasket(buyerId: string = null): CustomerBasket {
    return {
      buyerId,
      items: [],
    };
  }

}
