import Image from 'next/image';
import React from 'react';
import { Navbar } from 'components/organisms';

import '@/pages/_app.jsx';
import Card from 'components/Card';

import illustration__box from '@/pages/faq/images/illustration-box-desktop.svg';
import illustration__woman_desktop from '@/pages/faq/images/illustration-woman-online-desktop.svg';

const questionsAnswers = [
  {
    id: 0,
    question: '다수의 블록체인 노드에 개인정보가 공유되는 것 아닌가요?',
    answer: '블록체인에는 개인정보의 해쉬값이 공유되는 것이지 개인정보 자체가 공유되지 않습니다.',
  },
  {
    id: 1,
    question: '문서의 진행상황은 어떻게 확인하나요?',
    answer: '로그인 후 Menu-ChainTract에서 확인하실 수 있습니다.',
  },
  {
    id: 2,
    question: '1건의 계약 시 여러 명이 참여할 수 있나요?',
    answer: `네, 가능합니다. 여러 명의 참여자가 계약서에 서명할 수 있습니다.`,
  },
  {
    id: 3,
    question: '전자계약과 전자서명이 법적 효력이 있나요?',
    answer: `전자서명법 제3조 2항과 전자문서및전자거래기본법 제4조 제1항에 의해 법적 효력이 인정됩니다.`,
  },
  {
    id: 4,
    question: '메일이나 아이디 변경이 가능한가요?',
    answer: `ChainTract는 카카오톡으로 가입을 진행하기 때문에 변경을 지원하고 있지 않습니다. 새로 가입해주세요. `,
  },
];

const FaqTemplate = () => {
  return (
    <>
      <div className="navbar">
        <Navbar />
        <div className="container">
          <div className="component">
            <div className="illustration">
              <Image
                src={illustration__box}
                alt="illustration with box"
                className="illustration__box"
              />

              <Image
                className="illustration__woman-desktop"
                src={illustration__woman_desktop}
                alt="illustration with woman"
              />
            </div>
            <Card questionsAnswers={questionsAnswers} />
          </div>
        </div>
      </div>
    </>
  );
};

export default FaqTemplate;
