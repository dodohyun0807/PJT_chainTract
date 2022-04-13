import React from 'react';
import Styled from './Navbar.styled';
import { NavButton } from '@/components/atoms';
import { KakaoLoginBtn } from '@/components/molecules';

const Navbar = () => {
  let userInfo = '';
  if (typeof window !== 'undefined' && window.sessionStorage) {
    userInfo = sessionStorage.getItem('chainTractLoginInfo');
  }

  return (
    <Styled.MainContainer>
      <>
        <NavButton bgColor="inherit" color="#000" href="/">
          ChainTract
        </NavButton>
      </>
      {userInfo ? (
        <>
          <NavButton bgColor="inherit" color="#000" href="/contractpage">
            Contract
          </NavButton>
        </>
      ) : (
        <></>
      )}
      <>
        <NavButton bgColor="inherit" color="#000" href="/faq">
          FAQ
        </NavButton>
      </>
      <>
        <KakaoLoginBtn />
      </>
    </Styled.MainContainer>
  );
};

export default Navbar;
