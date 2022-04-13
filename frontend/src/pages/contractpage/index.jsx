import React from 'react';
import GuideTemplate from '@/template/guideTemplate';
import dynamic from 'next/dynamic';

const NoSSR = dynamic(() => import('@/template/contractPage'), {
  ssr: false,
});

const ContractPage = () => {
  let userInfo = false;
  if (typeof window !== 'undefined' && window.sessionStorage.chainTractLoginInfo) {
    userInfo = true;
  }

  const privateRoute = () => {
    if (userInfo === false) {
      return <GuideTemplate />;
    }
    return <NoSSR />;
  };

  return privateRoute();
};

export default ContractPage;
