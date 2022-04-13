import React from 'react';
import dynamic from 'next/dynamic';
import GuideTemplate from '@/template/guideTemplate';

const NoSSR = dynamic(() => import('@/template/contractDetail'), {
  ssr: false,
});

const ContractDetail = ({ contractId }) => {
  let userInfo = false;
  if (typeof window !== 'undefined' && window.sessionStorage.chainTractLoginInfo) {
    userInfo = true;
  }

  const privateRoute = () => {
    if (userInfo === false) {
      return <GuideTemplate />;
    }
    return <NoSSR contractId={contractId} />;
  };

  return privateRoute();
};

export async function getServerSideProps(context) {
  const data = context.query.contractId;

  return {
    props: { contractId: data },
  };
}

export default ContractDetail;
