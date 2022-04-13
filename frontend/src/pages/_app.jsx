import React from 'react';
import '@/pages/faq/scss/main.scss';
import '@/template/contractPage/renderPages/write/style.css';
import GlobalStyle from '@/styles/global';
import { Hydrate, QueryClient, QueryClientProvider } from 'react-query';
import { ReactQueryDevtools } from 'react-query/devtools';
import { AppLayout } from '@/components/organisms';

function MyApp({ Component, pageProps }) {
  const [queryClient] = React.useState(() => new QueryClient());

  return (
    <QueryClientProvider client={queryClient}>
      <Hydrate state={pageProps.dehydratedState}>
        <GlobalStyle />
        <AppLayout>
          <Component {...pageProps} />
        </AppLayout>
      </Hydrate>
      <ReactQueryDevtools />
    </QueryClientProvider>
  );
}

export default MyApp;
