import { Product } from './Product';
import { Product as ProductModel } from '../models';
import { Card } from 'react-bootstrap';

interface Props {
  products?: ProductModel[];
  addToCart: (product: ProductModel) => void;
}

export const ProductList = ({products = [], addToCart}: Props) => (
  <Card className="mt-2">
    <Card.Body className="d-flex justify-content-start flex-wrap">
      {
        products.map(product => (
          <Product
            key={product.id}
            product={product}
            addToCart={addToCart}
          />
        ))
      }
    </Card.Body>
  </Card>
);
