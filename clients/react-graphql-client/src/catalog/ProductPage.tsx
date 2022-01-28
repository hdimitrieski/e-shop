import { Container, Row } from 'react-bootstrap';
import { useQuery } from '@apollo/client';
import { loader } from 'graphql.macro';
import { Product as ProductModel } from './models';
import { Loader } from '../shared';
import { useParams } from 'react-router-dom';
import { Product } from './products/Product';
import { useAddToBasket } from '../core/hooks';

const productQuery = loader('./ProductQuery.graphql');

export const ProductPage = () => {
  const params = useParams();
  const {loading, data} = useQuery(productQuery, {
    variables: {
      id: params.productId
    }
  });
  const addToBasket = useAddToBasket();
  const product: ProductModel = data?.product;

  return loading
    ? <Loader/>
    : (
      <Container>
        <Row>
          <Product product={product} addToCart={() => addToBasket(product.id)}/>
        </Row>
      </Container>
    );
};
