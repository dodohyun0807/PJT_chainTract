import Link from 'next/link';
import React from 'react';
import Styled from './NavButton.styled';

const NavButton = ({ children, bgColor, color, href }) => {
  return (
    <Link href={href} passHref>
      <Styled.NavButton bgColor={bgColor} color={color}>
        {children}
      </Styled.NavButton>
    </Link>
  );
};

export default NavButton;
