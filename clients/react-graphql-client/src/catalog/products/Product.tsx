import { Image } from '../../shared';
import styles from './Product.module.scss';
import { Product as ProductModel } from '../models';
import { Link } from 'react-router-dom';

interface Props {
  product: ProductModel;
  addToCart: (product: ProductModel) => void;
}

export const Product = ({product, addToCart}: Props) => (
  <div className="p-1">
    <div className="image">
      <Image name={product.image}/>
    </div>
    <div className="d-flex flex-column justify-content-center align-items-center">
      <button className="col-12 btn btn-primary" onClick={() => addToCart(product)}>Add to card</button>
      <Link to={`/product/${product.id}`} className={styles.name}>{product.name}</Link>
      <div className={styles.price}>
        <span>$</span>
        <span>&nbsp;</span>
        <span>{product.price}</span>
      </div>
    </div>
  </div>
);
