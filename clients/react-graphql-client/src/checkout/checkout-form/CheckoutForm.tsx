import { useForm } from 'react-hook-form';
import { CheckoutFormData } from '../models';
import { Button, Form, Row } from 'react-bootstrap';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';

const REQUIRED_MESSAGE = 'The field is required';

const schema = yup.object({
  firstName: yup.string().required(REQUIRED_MESSAGE),
  lastName: yup.string().required(REQUIRED_MESSAGE),
  country: yup.string().required(REQUIRED_MESSAGE),
  city: yup.string().required(REQUIRED_MESSAGE),
  state: yup.string().required(REQUIRED_MESSAGE),
  street: yup.string().required(REQUIRED_MESSAGE),
  zipCode: yup.string().required(REQUIRED_MESSAGE),
  cardExpiration: yup.string().required(REQUIRED_MESSAGE),
  cardNumber: yup.string()
    .min(5, 'Card number must be at least 5 characters long')
    .max(19, 'Card number must not exceed 19 characters')
    .required(REQUIRED_MESSAGE),
  cardSecurityNumber: yup.string()
    .min(3, 'Security number must be 3 characters long')
    .max(3, 'Security number must be 3 characters long')
    .required(REQUIRED_MESSAGE),
}).required();

interface Props {
  onSubmit: (formData: CheckoutFormData) => void;
}

export const CheckoutForm = ({onSubmit}: Props) => {
  const {
    handleSubmit,
    formState: {
      errors,
      isValid
    },
    register
  } = useForm<CheckoutFormData>({
    resolver: yupResolver(schema),
    defaultValues: {
      firstName: ''
    },
    mode: 'onChange'
  });

  return (
    <Form onSubmit={handleSubmit(onSubmit)}>
      <h4 className="mb-3">Billing address</h4>

      <Row className="g-3">
        <Form.Group>
          <Form.Label>First name</Form.Label>
          <Form.Control
            {...register('firstName')}
            type="text"
            placeholder="Enter first name"
            isInvalid={!!errors.firstName}
          />
          <Form.Control.Feedback type="invalid">{errors.firstName?.message}</Form.Control.Feedback>
        </Form.Group>
        <Form.Group>
          <Form.Label>Last name</Form.Label>
          <Form.Control
            {...register('lastName')}
            type="text"
            placeholder="Enter last name"
            isInvalid={!!errors.lastName}
          />
          <Form.Control.Feedback type="invalid">{errors.lastName?.message}</Form.Control.Feedback>
        </Form.Group>
        <Form.Group>
          <Form.Label>Country</Form.Label>
          <Form.Control
            {...register('country')}
            type="text"
            placeholder="Enter country"
            isInvalid={!!errors.country}
          />
          <Form.Control.Feedback type="invalid">{errors.country?.message}</Form.Control.Feedback>
        </Form.Group>
        <Form.Group>
          <Form.Label>City</Form.Label>
          <Form.Control
            {...register('city')}
            type="text"
            placeholder="Enter city"
            isInvalid={!!errors.city}
          />
          <Form.Control.Feedback type="invalid">{errors.city?.message}</Form.Control.Feedback>
        </Form.Group>
        <Form.Group>
          <Form.Label>State</Form.Label>
          <Form.Control
            {...register('state')}
            type="text"
            placeholder="Enter state"
            isInvalid={!!errors.state}
          />
          <Form.Control.Feedback type="invalid">{errors.state?.message}</Form.Control.Feedback>
        </Form.Group>
        <Form.Group>
          <Form.Label>Street</Form.Label>
          <Form.Control
            {...register('street')}
            type="text"
            placeholder="Enter street"
            isInvalid={!!errors.street}
          />
          <Form.Control.Feedback type="invalid">{errors.street?.message}</Form.Control.Feedback>
        </Form.Group>
        <Form.Group>
          <Form.Label>Zip code</Form.Label>
          <Form.Control
            {...register('zipCode')}
            type="text"
            placeholder="Enter zip code"
            isInvalid={!!errors.zipCode}
          />
          <Form.Control.Feedback type="invalid">{errors.zipCode?.message}</Form.Control.Feedback>
        </Form.Group>
      </Row>

      <hr className="my-4"/>

      <h4 className="mb-3">Payment</h4>
      <Row className="gy-3">
        <Form.Group>
          <Form.Label>Card holder name</Form.Label>
          <Form.Control
            {...register('cardHolderName')}
            type="text"
            placeholder="Enter card holder name"
            isInvalid={!!errors.cardHolderName}
          />
          <Form.Text className="text-muted">Full name as displayed on card.</Form.Text>
          <Form.Control.Feedback type="invalid">{errors.cardHolderName?.message}</Form.Control.Feedback>
        </Form.Group>
        <Form.Group>
          <Form.Label>Credit card number</Form.Label>
          <Form.Control
            {...register('cardNumber')}
            type="text"
            placeholder="Enter credit card number"
            isInvalid={!!errors.cardNumber}
          />
          <Form.Control.Feedback type="invalid">{errors.cardNumber?.message}</Form.Control.Feedback>
        </Form.Group>
        <Form.Group>
          <Form.Label>Expiration</Form.Label>
          <Form.Control
            {...register('cardExpiration')}
            type="date"
            placeholder="Enter expiration"
            isInvalid={!!errors.cardExpiration}
          />
          <Form.Control.Feedback type="invalid">{errors.cardExpiration?.message}</Form.Control.Feedback>
        </Form.Group>
        <Form.Group>
          <Form.Label>CVV</Form.Label>
          <Form.Control
            {...register('cardSecurityNumber')}
            type="text"
            placeholder="Enter card security number"
            isInvalid={!!errors.cardSecurityNumber}
          />
          <Form.Control.Feedback type="invalid">{errors.cardSecurityNumber?.message}</Form.Control.Feedback>
        </Form.Group>
      </Row>

      <Button
        className="w-100"
        type="submit"
        disabled={!isValid}>
        Checkout
      </Button>
    </Form>
  );
};
