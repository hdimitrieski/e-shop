import { Card, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useQuery } from '@apollo/client';
import { OrdersQueryResponse } from './models';
import { loader } from 'graphql.macro';
import { Loader } from '../shared';
import dayjs from 'dayjs';

const ORDERS_QUERY = loader('./OrdersQuery.graphql');

export const OrdersPage = () => {
  const {data, loading, error} = useQuery<OrdersQueryResponse>(ORDERS_QUERY);

  if (loading) return <Loader/>;

  if (error) return null;

  return (
    <Card>
      <Card.Header>Orders</Card.Header>
      <Card.Body>
        <Table>
          <thead>
          <tr>
            <th>#</th>
            <th>Order Number</th>
            <th>Order Date</th>
            <th>Status</th>
            <th>Total Price</th>
          </tr>
          </thead>
          <tbody>
          {
            data?.me.orders.map((order, i) => (
              <tr key={order.id}>
                <td>{i + 1}</td>
                <td><Link to={`/orders/${order.id}`}>{order.id}</Link></td>
                <td>{dayjs(order.date).format('yyyy/MM/dd hh:mm:ss')}</td>
                <td>{order.status}</td>
                <td>{order.totalPrice}</td>
              </tr>
            ))
          }
          </tbody>
        </Table>
      </Card.Body>
    </Card>
  );
};
