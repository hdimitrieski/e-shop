import { Basket } from './Basket';

export interface BasketQueryResponse {
  me: {
    basket: Basket;
  }
}
