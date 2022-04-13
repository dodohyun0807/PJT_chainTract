import styled from '@emotion/styled';

const Heading = styled.h1`
  margin: 0vh 10vw 0vh 10vw;
  font-weight: 700;
  text-align: center;
  font-size: 3rem;
  color: ${(props) => props.color};
  background-color: ${(props) => props.bgColor};
`;

export default { Heading };
