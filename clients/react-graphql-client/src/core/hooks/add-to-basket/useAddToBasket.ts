import { useLazyQuery, useMutation } from '@apollo/client';
import { useCallback } from 'react';
import { loader } from 'graphql.macro';
import { AddToBasketInput } from './AddToBasketInput';
import { AddToBasketResponse } from './AddToBasketResponse';
import { BasketQueryResponse } from './BasketQueryResponse';

const BASKET_QUERY = loader('./BasketQuery.graphql');
const ADD_TO_BASKET_MUTATION = loader('./AddToBasketMutation.graphql');

export const useAddToBasket = () => {
  const [fetchBasket] = useLazyQuery<BasketQueryResponse>(BASKET_QUERY);
  const [addToBasket] = useMutation<AddToBasketResponse, AddToBasketInput>(ADD_TO_BASKET_MUTATION);

  const addToBasketFn = (lineItemId: string) =>
    fetchBasket()
      .then(({data, error}) => {
        if (!data || error) return Promise.reject(error);
        return data.me.basket.id;
      })
      .then((basketId) => addToBasket({
        variables: {input: {basketId, lineItemId}}
      }))
      .then(value => value.data);

  return useCallback(addToBasketFn, [fetchBasket, addToBasket]);
};
