import React, { useState, useCallback } from 'react';
import { apiInstance, fileInstance } from '@/libs/axios';
import { ModalBtn } from '@/components/organisms';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Styled from './styled';
import { useRouter } from 'next/router';

const Write = () => {
  const router = useRouter();
  const api = apiInstance();
  const fileApi = fileInstance();
  const [contractName, setContractName] = useState();
  const [covenantee, setCovenantee] = useState([]);
  const [covenanteeInput, setCovenanteeInput] = useState();
  const [files, setFile] = useState([]);
  const [isConfirm, setIsConfirm] = useState(false);

  const onFileChange = useCallback(
    (e) => {
      setFile([...Array.from(e.target.files)]);
    },
    [files],
  );

  const ChangeContractName = useCallback(
    (e) => {
      setContractName(e.target.value);
    },
    [contractName],
  );

  const ChangeCovenanteeInput = useCallback(
    (e) => {
      setCovenanteeInput(e.target.value);
    },
    [covenanteeInput],
  );

  const InputCovenantee = (e) => {
    e.preventDefault();
    if (covenanteeInput.length > 0) {
      setCovenantee([...covenantee, covenanteeInput]);
      setCovenanteeInput('');
    } else {
      alert('계약자(이메일주소)를 입력해주세요');
    }
  };

  const deleteCovenantee = (e) => {
    e.preventDefault();
    let value = [...covenantee];
    value.splice(e.target.id, 1);
    setCovenantee(value);
  };

  async function SubmitContract() {
    if (!contractName) {
      alert('제목을 입력해주세요');
      return;
    }
    if (covenantee.length === 0) {
      alert('계약자(이메일주소)를 추가해주세요');
      return;
    }
    if (files.length === 0) {
      alert('계약서(pdf파일)를 선택해주세요');
      return;
    }

    const formData = new FormData();
    let filePath = '';

    formData.append('files', '');
    files.forEach((file) => formData.append('files', file));

    await fileApi.post('/contract/file', formData).then((res) => {
      filePath = res.data.response;
    });

    const request = {
      name: contractName,
      participantEmails: [...covenantee],
      filePath: filePath,
    };

    await api.post('/contract', request).then(() => {
      alert('계약생성완료');
      router.push('/');
    });
  }

  return (
    <Styled.ContentContainer>
      <React.Fragment>
        <div className="text-center text-position" color="#000">
          <Typography variant="h5" gutterBottom>
            계약서 작성
          </Typography>
        </div>
        <div className="component-position">
          <Grid container spacing={5}>
            <Grid item xs={10}>
              <TextField
                required
                id="address1"
                name="제목"
                label="제목"
                fullWidth
                maxRows={4}
                value={contractName}
                onChange={ChangeContractName}
                autoComplete="제목"
                variant="standard"
              />
            </Grid>

            <Grid item xs={10}>
              <TextField
                required
                id="standard-multiline-flexible"
                name="계약자(이메일주소)"
                label="이메일 주소(계약자)"
                fullWidth
                maxRows={4}
                value={covenanteeInput}
                onChange={ChangeCovenanteeInput}
                autoComplete="이메일주소"
                variant="standard"
              />

              <input
                type="button"
                onClick={InputCovenantee}
                className="label theme-bg2 text-white f-12 btn-round shadow-2 button-position"
                value="추가"
              />
            </Grid>

            <div>
              {covenantee.map((covenantee, idx) => (
                <div key={covenantee + idx}>
                  <p className="covenantee-position convenantee-color">계약자: {covenantee}</p>
                  <input
                    type="button"
                    value="삭제"
                    onClick={deleteCovenantee}
                    id={idx}
                    className="label theme-bg2 text-white f-12 btn-round shadow-2 delete-position"
                  />
                </div>
              ))}
            </div>
          </Grid>
        </div>
      </React.Fragment>

      <div>
        <input
          type="file"
          name="file_upload"
          accept=".pdf"
          className="label theme-bg2 text-white f-12 btn-round shadow-2 file-position"
          onChange={onFileChange}
          multiple
        />
      </div>

      {isConfirm ? (
        <button
          onClick={SubmitContract}
          className="label theme-bg text-white f-12 btn-round shadow-2 submit-position"
          sx={{ mt: 3, ml: 1 }}
        >
          Submit
        </button>
      ) : (
        <ModalBtn viewSubmitBtn={setIsConfirm} />
      )}
    </Styled.ContentContainer>
  );
};

export default Write;
