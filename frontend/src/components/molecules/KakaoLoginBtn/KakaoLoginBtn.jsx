import React from 'react';
import Styled from './KakaoLoginBtn.styled';
import KakaoLogin from 'react-kakao-login';
import { useRouter } from 'next/router';
import { authInstance } from '@/libs/axios';
import { KAKAO_OAUTH_APIKEY } from '@/config';

const KakaoLoginBtn = () => {
  const router = useRouter();
  const api = authInstance();

  let userInfo = '';
  if (typeof window !== 'undefined' && window.sessionStorage) {
    userInfo = sessionStorage.getItem('chainTractLoginInfo');
  }

  return (
    <KakaoLogin
      token={KAKAO_OAUTH_APIKEY}
      onLogout={() => {
        sessionStorage.removeItem('chainTractLoginInfo');
        api.get('/auth/logout');
        router.push('/');
      }}
      onSuccess={(res) => {
        sessionStorage.setItem('chainTractLoginInfo', res.profile.kakao_account.email);
        api.post(
          '/auth/login',
          { accesstoken: res.response.access_token },
          { withCredentials: true },
        );
        router.push('/');
      }}
      onFail={() => {}}
      render={({ onClick }) => (
        <Styled.MainContainer
          onClick={(e) => {
            e.preventDefault();
            onClick();
          }}
        >
          {userInfo ? <div>Sign out</div> : <div>Sign in</div>}
        </Styled.MainContainer>
      )}
    />
  );
};

export default KakaoLoginBtn;
