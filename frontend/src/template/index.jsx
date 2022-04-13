import React, { useState, useEffect, useCallback } from 'react';
import { Navbar } from '@/components/organisms';
import Styled from './styled';
import Image from 'next/image';
import illustration__security from '/public/secure_image.gif';
import illustration__main from '/public/main.png';
import page2 from '/public/page2.png';
import page3 from '/public/page3.png';
import page4 from '/public/page4.png';
import page5 from '/public/page5.png';

const index = () => {
  const [scrollHeight, setScrollHeight] = useState(0);

  const onWheel = useCallback(
    (e) => {
      e.preventDefault();
      if (e.deltaY < 0 && scrollHeight !== 0) {
        setScrollHeight(scrollHeight + 92);
      } else if (e.deltaY > 0 && scrollHeight !== -368) {
        setScrollHeight(scrollHeight - 92);
      }
    },
    [scrollHeight],
  );

  useEffect(() => {
    window.addEventListener('wheel', onWheel, { passive: false });
    return () => {
      window.removeEventListener('wheel', onWheel);
    };
  }, [scrollHeight]);

  return (
    <>
      <div className="navbar">
        <Navbar />
        <Styled.MainContainer>
          <Styled.ContentContainer scrollHeight={scrollHeight}>
            <Styled.stylePage1>
              <Styled.imageStyle>
                <Image src={illustration__main} />
              </Styled.imageStyle>
              <Styled.styleP>
                스크롤을 아래로 내려주세요
                <Styled.MovingArrow />
              </Styled.styleP>
            </Styled.stylePage1>
          </Styled.ContentContainer>

          <Styled.ContentContainer scrollHeight={scrollHeight}>
            <Styled.stylePage2>
              <Styled.imageStyle2>
                <Image src={illustration__security} width={550} height={550} />
              </Styled.imageStyle2>
              <Styled.imageStyleChaintract>
                <Image src={page2} />
              </Styled.imageStyleChaintract>
            </Styled.stylePage2>
          </Styled.ContentContainer>

          <Styled.ContentContainer scrollHeight={scrollHeight}>
            <Styled.stylePage3>
              <Image src={page3} />
            </Styled.stylePage3>
          </Styled.ContentContainer>

          <Styled.ContentContainer scrollHeight={scrollHeight}>
            <Styled.stylePage4>
              <Image src={page4} />
            </Styled.stylePage4>
          </Styled.ContentContainer>

          <Styled.ContentContainer scrollHeight={scrollHeight}>
            <Styled.stylePage5>
              <Image src={page5} />
            </Styled.stylePage5>
          </Styled.ContentContainer>
        </Styled.MainContainer>
      </div>
    </>
  );
};

export default index;
