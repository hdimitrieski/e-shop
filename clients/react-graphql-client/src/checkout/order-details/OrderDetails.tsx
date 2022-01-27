import { Basket } from '../models';
import { Image, ImageSize } from '../../shared';

interface Props {
  basket: Basket;
}

export const OrderDetails = ({basket}: Props) => {
  return (
    <>
      <h4 className="d-flex justify-content-between align-items-center mb-3">
        <span className="text-primary">Your basket</span>
        <span className="badge bg-primary rounded-pill">{basket.lineItems.length}</span>
      </h4>

      <ul className="list-group mb-3">
        {
          basket.lineItems.map(lineItem => (
            <li key={lineItem.id} className="list-group-item d-flex justify-content-between lh-sm">
              <div className="d-flex align-items-center">
                <Image name={lineItem.image} size={ImageSize.Small}/>
                <h6 className="my-0 p-1">{lineItem.name}</h6>
              </div>
              <span className="text-muted">
                {lineItem.quantity} X {lineItem.unitPrice} = {lineItem.price}
              </span>
            </li>
          ))
        }
        <li className="list-group-item d-flex justify-content-between">
          <span>Total</span>
          <strong>{basket.totalPrice}</strong>
        </li>
      </ul>
    </>
  );
}
