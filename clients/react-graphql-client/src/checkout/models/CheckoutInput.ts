import { Address } from './Address';
import { CardDetails } from './CardDetails';

export interface CheckoutInput {
  input: {
    address: Address;
    cardDetails: CardDetails;
  }
}
