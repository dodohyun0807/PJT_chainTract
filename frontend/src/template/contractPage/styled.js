import styled from '@emotion/styled';

const MainContainer = styled.div`
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
  color: black;
  width: 100%;
  height: 100%;
  font-size: 1.0rem;
  font-family: arial;
`;

const ListContainer = styled.div`
  display: flex;
  flex-flow: column nowrap;
  width: 15vw;
  color: white;

`;

const ViewContainer = styled.div`
  display: flex;
  flex-flow: column nowrap;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 91vh;
`;

export default {
  MainContainer,

  ViewContainer,
  ListContainer,
};
