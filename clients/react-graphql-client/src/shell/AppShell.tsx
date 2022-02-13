import { useAuth } from 'oidc-react';
import { ApolloClient, ApolloLink, ApolloProvider, createHttpLink, InMemoryCache } from '@apollo/client';
import { sha256 } from 'crypto-hash';
import { setContext } from '@apollo/client/link/context';
import * as R from 'ramda';
import { Layout } from './Layout';
import { createPersistedQueryLink } from '@apollo/client/link/persisted-queries';

export const AppShell = ({children}: { children: JSX.Element }) => {
  const auth = useAuth();

  const authLink: ApolloLink = setContext((_, {headers}) => {
    // get the authentication token from local storage if it exists
    // const token = localStorage.getItem('token');
    // return the headers to the context so httpLink can read them
    return {
      headers: {
        ...headers,
        authorization: auth.userData?.access_token
          ? `${auth.userData?.token_type} ${auth.userData?.access_token}`
          : '',
      }
    }
  });

  const persistedQueryLink = createPersistedQueryLink({
    sha256
  });

  const httpLink = createHttpLink({
    uri: process.env.REACT_APP_GRAPHQL_SERVER_URL,
  });

  // Make the pagination compliant with offset limit and use offsetLimitPagination()
  const client = new ApolloClient({
    link: authLink
      .concat(persistedQueryLink)
      .concat(httpLink),
    connectToDevTools: process.env.NODE_ENV === 'development',
    cache: new InMemoryCache({
      typePolicies: {
        Query: {
          fields: {
            products: {
              // Don't cache separate results based on any of this field's arguments.
              keyArgs: false,
              // Concatenate the incoming list items with the existing list items.
              merge(existing = {}, incoming) {
                return R.mergeRight(existing, {
                  [`${incoming.page}-${incoming.pageSize}`]: incoming
                });
              },
              read(existing, {args: {page, pageSize}}: any) {
                return existing && existing[`${page}-${pageSize}`];
              }
            },
            me: {
              merge: true
            }
          }
        }
      }
    })
  });

  return !auth.isLoading ? (
    <ApolloProvider client={client}>
      <Layout>
        {children}
      </Layout>
    </ApolloProvider>
  ) : null;
};
