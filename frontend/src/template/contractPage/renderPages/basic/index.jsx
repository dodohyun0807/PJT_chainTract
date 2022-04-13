import React from 'react';
import Image from 'next/image';
import illustration__contract from "../../../../pages/faq/images/contract.png";
import Styled from './styled';

const Basic = () => {
  return (
    <Styled.imageStyle>
      <Image
        src={illustration__contract}
      />

    </Styled.imageStyle>
  
  );
};

export default Basic;
