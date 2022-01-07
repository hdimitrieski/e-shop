import { OrderItem } from './order-item';

export interface Order {
  orderNumber: string;
  date: Date;
  description: string;
  street: string;
  city: string;
  state: string;
  zipCode: string;
  country: string;
  total: number;
  status: string;
  orderItems: OrderItem[];
}
