import { Order } from './models/Order';

export interface OrdersQueryResponse {
  me: {
    orders: Order[];
  }
}
