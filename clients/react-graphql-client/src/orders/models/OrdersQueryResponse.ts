import { Order } from './Order';

export interface OrdersQueryResponse {
  me: {
    orders: Order[];
  }
}
