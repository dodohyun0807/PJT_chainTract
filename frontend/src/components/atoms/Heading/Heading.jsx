import React from 'react';

import Styled from './Heading.styled';

const Heading = ({ children, bgColor, color }) => {
  return (
    <Styled.Heading bgColor={bgColor} color={color}>
      {children}
    </Styled.Heading>
  );
};

export default Heading;
