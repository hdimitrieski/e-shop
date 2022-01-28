
export interface CheckoutForm {
  city: string;
  street: string;
  state: string;
  country: string;
  zipCode: string;
  cardNumber: string;
  cardHolderName: string;
  cardExpiration: number[];
  cardSecurityNumber: string;
  cardType: string;
  buyer: string;
}
