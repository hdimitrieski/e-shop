import { Container, Pagination, Row } from 'react-bootstrap';
import { ProductList } from './products/ProductList';
import { useQuery } from '@apollo/client';
import { loader } from 'graphql.macro';
import { Page } from '../core/models';
import { Loader } from '../shared';
import * as R from 'ramda';
import { useSearchParams } from 'react-router-dom';
import { useEffect } from 'react';
import { useAddToBasket } from '../core/hooks';
import { Product } from './models';

const CATALOG_PAGE_QUERY = loader('./CatalogPageQuery.graphql');

const toPaginationQueryParams = (page: number, pageSize: number): Record<string, string> => ({
  page: String(page),
  pageSize: String(pageSize)
});

const toPaginationVariables = (params: URLSearchParams) => ({
  page: Number(params.get('page')),
  pageSize: Number(params.get('pageSize'))
})

export const CatalogPage = () => {
  const [searchParams, setSearchParams] = useSearchParams(toPaginationQueryParams(0, 10));
  const {loading, data, error, fetchMore} = useQuery(CATALOG_PAGE_QUERY, {
    variables: toPaginationVariables(searchParams),
    notifyOnNetworkStatusChange: true
  });
  const addToBasket = useAddToBasket();
  const productPage: Page<Product> = data?.products;

  useEffect(() => {
    const nextPage = Number(searchParams.get('page'));
    if (productPage?.page === undefined || nextPage === productPage.page) return;

    fetchMore({
      variables: {
        page: nextPage,
        pageSize: productPage.pageSize
      }
    })
  });

  const numberOfPages = () => Math.ceil(productPage.total / productPage.pageSize);

  const changePage = (nextPage: number) => {
    setSearchParams(toPaginationQueryParams(nextPage, productPage.pageSize));
  };

  if (loading) return (<Loader/>);

  if (error) return null;

  return (
    <Container>
      <Row>
        <ProductList
          products={productPage?.result}
          addToCart={(product) => addToBasket(product.id)}
        />
      </Row>

      <Row>
        <Pagination>
          {
            R.times(pageNumber => (
                <Pagination.Item
                  key={pageNumber}
                  active={pageNumber === productPage.page}
                  onClick={() => changePage(pageNumber)}>
                  {pageNumber}
                </Pagination.Item>
              ),
              numberOfPages()
            )
          }
        </Pagination>
      </Row>
    </Container>
  );
};
