import { useMutation, useQuery } from '@apollo/client';
import { loader } from 'graphql.macro';
import { Image, ImageSize, Loader } from '../shared';
import { Basket, BasketQueryResponse } from './models';
import { Button, Card, Col, Container, Row, Table } from 'react-bootstrap';
import NumberFormat from 'react-number-format';
import { useNavigate } from 'react-router-dom';
import { LineItem } from './models/LineItem';

const BASKET_QUERY = loader('./BasketQuery.graphql');
const ADD_QUANTITY_MUTATION = loader('./AddQuantityMutation.graphql');
const REMOVE_QUANTITY_MUTATION = loader('./RemoveQuantityMutation.graphql');

export const BasketPage = () => {
  const {loading, data, error} = useQuery<BasketQueryResponse>(BASKET_QUERY, {
    fetchPolicy: 'cache-and-network',
    nextFetchPolicy: 'cache-first'
  });
  const [addQuantity] = useMutation(ADD_QUANTITY_MUTATION);
  const [removeQuantity] = useMutation(REMOVE_QUANTITY_MUTATION);
  const navigate = useNavigate();
  const basketId = data?.me.basket.id;

  const handleAddQuantity = (lineItem: LineItem) => {
    const basket = data?.me.basket as Basket;

    addQuantity({
      variables: {
        input: {
          basketId,
          lineItemId: lineItem.id
        }
      },
      optimisticResponse: {
        addQuantity: {
          basket: {
            __typename: 'Basket',
            id: lineItem.id,
            lineItemsQuantity: basket.lineItemsQuantity + 1,
            totalPrice: basket.totalPrice + lineItem.price,
            lineItems: basket.lineItems.map(li =>
              li.id === lineItem.id
                ? {...li, quantity: li.quantity + 1}
                : li
            )
          }
        }
      }
    });
  };

  const handleRemoveQuantity = (lineItem: LineItem) => {
    if (lineItem.quantity <= 0) return;

    const basket = data?.me.basket as Basket;

    removeQuantity({
      variables: {
        input: {
          basketId,
          lineItemId: lineItem.id
        }
      },
      optimisticResponse: {
        removeQuantity: {
          basket: {
            __typename: 'Basket',
            id: lineItem.id,
            lineItemsQuantity: basket.lineItemsQuantity - 1,
            totalPrice: basket.totalPrice - lineItem.price,
            lineItems: basket.lineItems.map(li =>
              li.id === lineItem.id
                ? {...li, quantity: li.quantity - 1}
                : li
            )
          }
        }
      }
    });
  };

  const backToShopping = () => navigate(-1);

  const checkout = () => navigate('/checkout');

  if (loading) return (<Loader/>);

  if (error) return null;

  return (
    <Card>
      <Card.Header>Basket</Card.Header>
      <Card.Body>
        <Table striped bordered hover>
          <thead>
          <tr>
            <th>#</th>
            <th>Product Name</th>
            <th>Unit Price</th>
            <th>Quantity</th>
            <th>Price</th>
          </tr>
          </thead>
          <tbody>
          {
            data?.me.basket.lineItems.map((lineItem, i) => (<tr key={i}>
              <td>{i + 1}</td>
              <td>
                <Row xs="auto">
                  <Col>
                    <Image name={lineItem.image} size={ImageSize.Small}/>
                  </Col>
                  <Col>
                    {lineItem.name}
                  </Col>
                </Row>
              </td>
              <td>
                <NumberFormat
                  value={lineItem.unitPrice}
                  displayType="text"
                  thousandSeparator={true}
                  decimalScale={2}
                  prefix="$"
                />
              </td>
              <td>
                <span>{lineItem.quantity}</span>
                <Button onClick={() => handleAddQuantity(lineItem)}>+</Button>
                <Button onClick={() => handleRemoveQuantity(lineItem)}>-</Button>
              </td>
              <td>
                <NumberFormat
                  value={lineItem.price}
                  displayType="text"
                  thousandSeparator={true}
                  decimalScale={2}
                  prefix="$"
                />
              </td>
            </tr>))
          }
          </tbody>
        </Table>
        <Container>
          <Row>
            <Button onClick={backToShopping} variant="secondary">Back To Shopping</Button>
            <Button onClick={checkout}>Checkout</Button>
          </Row>
        </Container>
      </Card.Body>
    </Card>
  );
};
