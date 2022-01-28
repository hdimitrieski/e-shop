import { Card, Col, Row } from 'react-bootstrap';
import { useMutation, useQuery } from '@apollo/client';
import { loader } from 'graphql.macro';
import { BasketQueryResponse, CheckoutFormData, CheckoutInput } from './models';
import { Loader } from '../shared';
import { OrderDetails } from './order-details/OrderDetails';
import { CheckoutForm } from './checkout-form/CheckoutForm';
import { useNavigate } from 'react-router-dom';

const BASKET_QUERY = loader('./BasketQuery.graphql');
const CHECKOUT_MUTATION = loader('./CheckoutMutation.graphql');

export const CheckoutPage = () => {
  const {loading, data, error} = useQuery<BasketQueryResponse>(BASKET_QUERY);
  const [checkout] = useMutation<any, CheckoutInput>(CHECKOUT_MUTATION);
  const navigate = useNavigate();

  if (loading) return <Loader/>;

  if (!data || error) return null;

  const onCheckout = (data: CheckoutFormData) => {
    checkout({
      variables: {
        input: {
          address: {
            city: data.city,
            state: data.state,
            street: data.state,
            zipCode: data.zipCode,
            country: data.country
          },
          cardDetails: {
            cardHolderName: data.cardHolderName,
            expiration: data.cardExpiration,
            number: data.cardNumber,
            securityNumber: data.cardSecurityNumber,
            type: 'Visa'
          }
        }
      }
    }).then(() => navigate('/'));
    // TODO HD clear the basket
  };

  return (
    <Card>
      <Card.Header>Checkout</Card.Header>
      <Card.Body>
        <Row className="g-5">
          <Col md={7}>
            <CheckoutForm onSubmit={onCheckout}/>
          </Col>
          <Col md={5}>
            <OrderDetails basket={data.me.basket}/>
          </Col>
        </Row>
      </Card.Body>
    </Card>
  );
};
