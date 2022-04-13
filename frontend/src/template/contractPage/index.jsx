import React, { useState, useCallback } from 'react';
import Styled from './styled';
import { Navbar } from 'components/organisms';
import { ContractListMenu } from 'components/molecules';
import { Basic, CompleteServer, CompleteBlock, Ongoing, Sign, Write } from './renderPages';

const ContractPageTemplate = () => {
  const [pageState, setPageState] = useState(0);

  const renderPage = useCallback(() => {
    switch (pageState) {
      case 0:
        return <Basic />;
      case '작성':
        return <Write />;
      case '승인 대기':
        return <Sign />;
      case '성립전':
        return <Ongoing />;
      case '서버':
        return <CompleteServer />;
      case '블록체인':
        return <CompleteBlock />;
    }
  }, [pageState]);

  return (
    <>
      <div className="navbar">
        <Navbar />
        <Styled.MainContainer>
          <ContractListMenu handleState={setPageState} />
          <Styled.ViewContainer>
            <>{renderPage()}</>
          </Styled.ViewContainer>
        </Styled.MainContainer>
        ;
      </div>
    </>
  );
};

export default ContractPageTemplate;
