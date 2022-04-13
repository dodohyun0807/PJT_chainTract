import styled from '@emotion/styled';

const MainContainer = styled.div`
  display: flex;
  flex-flow: column nowrap;
  align-items: center;
  padding: 3vh 0 3vh 0;
`;

const BtnContainer = styled.div`
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
  align-items: center;
  padding: 3vh 3vw 3vh 3vw;
  width: 50vw;
`;

const BoxStyle = styled.div`
  color: black;
  font-size: 1.5em;
  font-weight: bold;
  display: flex;
  flex-flow: column nowrap;
  align-items: center;
  justify-content: space-between;
  height: 10vh;
  padding: 2vh 2vw 2vh 2vw;
  border-radius: 5px;
`;

export default { MainContainer, BtnContainer, BoxStyle };
