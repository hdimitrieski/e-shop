import { OrderDraftItem } from './order-draft-item';

export interface OrderDraft {
  total: number;
  orderItems: OrderDraftItem[];
}
