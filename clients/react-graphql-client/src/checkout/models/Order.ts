import { LineItem } from '../../basket/models/LineItem';

export interface Order {
  lineItemsQuantity: number;
  totalPrice: number;
  lineItems: LineItem[];
}
