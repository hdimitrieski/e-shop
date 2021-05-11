import { BasketItem } from "./basketItem";

export interface CustomerBasket {
  buyerId: number;
  items: BasketItem[];
}