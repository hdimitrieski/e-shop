import { BasketItem } from './basket-item';

export interface CustomerBasket {
  buyerId: string;
  items: BasketItem[];
}
