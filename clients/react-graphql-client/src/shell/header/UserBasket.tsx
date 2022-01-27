import styles from './UserBasket.module.scss';
import { useNavigate } from 'react-router-dom';
import { Basket } from './models';
import NumberFormat from 'react-number-format';

interface Props {
  basket: Basket;
}

export const UserBasket = ({basket}: Props) => {
  const navigate = useNavigate();

  const goToBasket = () => {
    navigate('/basket')
  }

  return (
    <div className={styles.cart + " mr-2"}>
      <div className="d-flex flex-row align-items-center justify-content-end">
        <div onClick={goToBasket} className={styles.cart_icon}>
          <img src="cart.png" alt="Basket"/>
          <div className={styles.cart_count}>
            <span>{basket.lineItemsQuantity}</span>
          </div>
        </div>
        <div className={styles.cart_content}>
          <div className="cart_price">
            <NumberFormat
              value={basket.totalPrice}
              displayType="text"
              thousandSeparator={true}
              prefix="$"
            />
          </div>
        </div>
      </div>
    </div>
  );
};
