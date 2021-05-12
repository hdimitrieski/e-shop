import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Basket } from "../models/basket";
import { CustomerBasket } from "../models/customerBasket";

@Injectable()
export class BasketService {

  constructor(private http: HttpClient) {
  }

  updateBasket(basketItems: Basket) {
    return this.http.post<CustomerBasket>('/api/v1/basket', basketItems) as Observable<CustomerBasket>;
  }

  getBasket(customerId: string) {
    return this.http.get<CustomerBasket>(`/api/v1/basket/${customerId}`) as Observable<CustomerBasket>;
  }
}