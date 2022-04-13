import styled from '@emotion/styled';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

const MainContainer = styled.div`
  width: 1535px;
  height: 92vh;
  overflow: hidden;
`;

const ContentContainer = styled.div`
  width: 100%;
  height: 100%;
  position: relative;
  top: ${(props) => props.scrollHeight}vh;
  transition: all 1.5s;
`;

const imageStyle = styled.div`
  width: 700px;
`;

const imageStyle2 = styled.div`
  position: fixed;
  left: -50px;
  top: -50px;
`;

const imageStyleChaintract = styled.div`
  position: relative;
  left: 500px;
  width: 800px;
  top: -50px;
`;

const stylePage1 = styled.h1`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 13em;
  font-weight: bold;
  line-height: 12vh;
  color: #fff;
  text-shadow: 4px 4px 4px rgba(191, 7, 139, 0.1);
`;
const stylePage2 = styled.h1`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 4em;
  font-weight: bold;
  text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.6);
  line-height: 6vh;
  width: 80%;
`;
const stylePage3 = styled.h1`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 5em;
  font-weight: bold;
  text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.6);
  letter-spacing: 0;
  line-height: 7vh;
  width: 80%;
`;
const stylePage4 = styled.h1`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 5em;
  font-weight: bold;
  text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.6);
  line-height: 7vh;
  width: 80%;
`;
const stylePage5 = styled.div`
  color: black;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 3em;
  font-weight: bold;
  text-shadow: 4px 4px 4px rgba(0, 0, 0, 0.6);
  width: 80%;
`;

const styleP = styled.p`
  display: flex;
  flex-flow: column nowrap;
  justify-content: center;
  align-items: center;
  font-size: 0.1em;
  margin-top: 5vh;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
`;

const styleDiv = styled.div`
  display: flex;
  flex-flow: column nowrap;
  align-items: center;
  line-height: 3vh;
  font-size: 0.7em;
`;

const page5Container = styled.div`
  display: flex;
  flex-flow: row wrap;
  justify-content: space-around;
  align-items: flex-start;
  margin: 5vh 0 0 0;
  text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.2);
`;

const arrow = styled.div`
  padding: 7vh 1vw 0 1vw;
`;

const MovingArrow = styled(ExpandMoreIcon)`
  animation: text-pop-up-bottom 1s cubic-bezier(0.25, 0.46, 0.45, 0.94) both infinite;
  @keyframes text-pop-up-bottom {
    0% {
      transform: translateY(0);
      transform-origin: 50% 50%;
      text-shadow: none;
    }
    100% {
      transform: translateY(30px);
      transform-origin: 50% 50%;
      text-shadow: 0 1px 0 #cccccc, 0 2px 0 #cccccc, 0 3px 0 #cccccc, 0 4px 0 #cccccc,
        0 5px 0 #cccccc, 0 6px 0 #cccccc, 0 7px 0 #cccccc, 0 8px 0 #cccccc, 0 9px 0 #cccccc,
        0 50px 30px rgba(0, 0, 0, 0.3);
    }
  }
`;

export default {
  MainContainer,
  ContentContainer,
  stylePage1,
  stylePage2,
  stylePage3,
  stylePage4,
  stylePage5,
  styleP,
  styleDiv,
  page5Container,
  arrow,
  imageStyle,
  imageStyle2,
  imageStyleChaintract,
  MovingArrow,
};
