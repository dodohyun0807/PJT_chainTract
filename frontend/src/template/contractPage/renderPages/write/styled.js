import styled from '@emotion/styled';

const MainContainer = styled.div`
  display: flex;
  flex-flow: row nowrap;
  text-align: center;
  background-color: white;
  justify-content: space-between;
`;

const ContentContainer = styled.div`
  box-shadow: 9px 9px 5px 1px rgba(29,233,182,.5);
  width: 595px;
  height: 595px;
  background-color: white;
  position: relative;
  left: 30px;
  top: 5px;
  transition: all 1.5s;
`;


export default {
  MainContainer,
  ContentContainer,
};
