import React from 'react';
import Link from 'next/link';
import Styled from './styled';
import { Heading } from '@/components/atoms';
import { Navbar } from '@/components/organisms';
import Button from '@material-ui/core/Button';

const GuideTemplate = () => {
  return (
    <>
      <Styled.MainContainer>
        <Heading>로그인 후 이용해주세요</Heading>
        <Styled.BtnContainer>
          <Styled.BoxStyle>
            문의 사항이 있으신가요?
            <Link href="/faq" passHref>
              <Button variant="contained" color="secondary" disableElevation>
                FAQ로 가기
              </Button>
            </Link>
          </Styled.BoxStyle>
          <Styled.BoxStyle>
            로그인이 필요하세요?
            <Link href="/" passHref>
              <Button variant="contained" color="secondary" disableElevation>
                홈으로 가기
              </Button>
            </Link>
          </Styled.BoxStyle>
        </Styled.BtnContainer>
      </Styled.MainContainer>
    </>
  );
};

export default GuideTemplate;
