import styled from '@emotion/styled';

const NavButton = styled.a`
  border: none;
  outline: none;
  cursor: pointer;
  font-size: 1.3rem;
  font-family: arial;
  background-color: ${(props) => props.bgColor};
  color: ${(props) => props.color};
  :hover {
    transform: scale(1.1);
    transition: 1s;
  }
`;

export default { NavButton };
