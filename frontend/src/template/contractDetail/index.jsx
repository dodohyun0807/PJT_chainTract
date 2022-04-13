import React from 'react';
import Styled from './styled';
import Button from '@material-ui/core/Button';
import { useRouter } from 'next/router';
import { useQuery } from 'react-query';
import { apiInstance } from '@/libs/axios';
import Typography from '@mui/material/Typography';
import image__loading from './Spinner-1s-200px.svg';
import Image from 'next/image';
import { Navbar } from 'components/organisms';
import { PDFReader } from 'reactjs-pdf-reader';

const ContractDetailTemplate = ({ contractId }) => {
  const contractId = contractId;
  const router = useRouter();
  let userInfo = '';
  if (typeof window !== 'undefined' && window.sessionStorage) {
    userInfo = sessionStorage.getItem('chainTractLoginInfo');
  }
  const api = apiInstance();

  const contractData = useQuery('contractData', () => api.get(`/contract/${contractId}`));
  if (contractData.isLoading)
    return (
      <Styled.ContentContainer>
        <Typography variant="h5" gutterBottom>
          Loading...
        </Typography>
        <Image src={image__loading} alt="image__loading" className="image__loading" />
      </Styled.ContentContainer>
    );
  if (contractData.isError)
    return (
      <Styled.ContentContainer>
        <Typography variant="h5" gutterBottom>
          An error has occurred:
        </Typography>
      </Styled.ContentContainer>
    );

  const confirmServer = () => {
    api.put(`/contract/sign/${contractId}`);
    alert('계약을 승인했습니다');
    router.replace('/contractpage');
  };

  const confirmBlock = () => {
    api.put(`/contract/${contractId}/block`);
    alert('계약을 승인했습니다');
    router.replace('/contractpage');
  };

  return (
    <div className="navbar">
      <Navbar />
      <Styled.MainContainer>
        <Styled.layoutBox>
          <Styled.contractContainer>
            <Typography
              variant="h2"
              color="#fff"
              text-shadow="4px 4px 4px rgba(191, 7, 139, 0.7);"
              gutterBottom
              maxWidth={'30vw'}
            >
              {contractData.data.data.response.name}
            </Typography>
            <Styled.ArticleArea>
              {contractData.data.data.response.participantEmails.map((covenantor) => {
                return <h2 key={covenantor}>계약자 : {covenantor}</h2>;
              })}

              <h2>날짜 : {contractData.data.data.response.createdDate.slice(0, 10)}</h2>

              {contractData.data.data.response.establishedDate !== null ? (
                <h2>
                  계약 체결 날짜 : {contractData.data.data.response.establishedDate.slice(0, 10)}
                </h2>
              ) : (
                <></>
              )}

              {contractData.data.data.response.txHashes.length > 0 ? (
                <>
                  <h2>계약 해쉬값 : </h2>
                  {contractData.data.data.response.txHashes.map((hash) => {
                    return <h2>{hash}</h2>;
                  })}
                </>
              ) : (
                <></>
              )}
              {contractData.data.data.response.uploadedDate === null ? (
                <>
                  <h2>(블록체인에 완전히 반영되지 않음) </h2>
                </>
              ) : (
                <></>
              )}
            </Styled.ArticleArea>

            {contractData.data.data.response.establishedDate === null ? (
              <>
                <Styled.warningH>
                  주의 : 계약을 승인하면 기록에서 삭제할 수 없습니다.
                </Styled.warningH>

                <Button
                  variant="contained"
                  className="label theme-bg text-white f-12"
                  disableElevation
                  onClick={confirmServer}
                >
                  승인
                </Button>
              </>
            ) : (
              <></>
            )}

            {contractData.data.data.response.establishedDate !== null &&
            contractData.data.data.response.uploadedDate === null ? (
              <>
                <Styled.warningH>
                  주의 : 계약을 승인하면 기록에서 삭제할 수 없습니다.
                </Styled.warningH>
                <Styled.warningH>
                  블록체인 계약 승인은 계약이 성립되는데 다소 시간이 걸릴 수 있습니다.
                </Styled.warningH>
                <Styled.warningH>
                  성립이 완료 되면 이행중(블록체인) 으로 자동으로 옮겨집니다.
                </Styled.warningH>

                <Button
                  variant="contained"
                  className="label theme-bg text-white f-12"
                  disableElevation
                  onClick={confirmBlock}
                >
                  블록체인 승인
                </Button>
              </>
            ) : (
              <></>
            )}
          </Styled.contractContainer>
          <div style={{ overflow: 'scroll', height: '80vh', width: '55vw' }}>
            <PDFReader
              url={`https://j6c105.p.ssafy.io/api/contract/${contractId}/file`}
              showAllPage={true}
            />
          </div>
        </Styled.layoutBox>
      </Styled.MainContainer>
    </div>
  );
};

export default ContractDetailTemplate;
