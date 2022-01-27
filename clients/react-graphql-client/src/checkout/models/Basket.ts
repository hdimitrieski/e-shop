import { LineItem } from './LineItem';

export interface Basket {
  id: string;
  totalPrice: number;
  lineItemsQuantity: number;
  lineItems: LineItem[];
}
