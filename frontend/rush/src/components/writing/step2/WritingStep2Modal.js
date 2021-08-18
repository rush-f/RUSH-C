import React from 'react';
import styled from "styled-components";
import Modal from 'react-modal';
import CancelButton from "../CancelButton";
import StyledInput from "./StyledInput";
import StyledTextarea from "./StyledTextarea";
import ToStep3Button from "./ToStep3Button";
import BackButton from "./BackButton";
import WindowSize from "../../../util/WindowSize";

const BoxInModal = styled.div`
  width: 85%;
  text-align: center;
`;
const Buttons = styled.div`
  width: 112%;
`;
const WritingStep2Modal = (props) => {

  return (
      <Modal            //모달 태그 내부에 옵션을 넣고 태그 사이에 내용을 넣는다
          isOpen={props.step === 2}        //모달의 오픈유무를 bool값으로 정한다.
          shouldCloseOnOverlayClick={false}     //close버튼을 눌러야만 모달이 종료
          //onRequestClose={closeModal}    // 모달이 닫히는 요청을 받으면 무엇을 할껀지
          style={{
            overlay: {
              backgroundColor: '#00112255'
            },
            content: {
              maxWidth: '500px',
              margin: 'auto',
              backgroundColor: 'white',
            }
          }}
          contentLabel="Example Modal"       //모달의 라벨
      >
        <CancelButton/>
        <BoxInModal>
        <StyledInput
            value={props.title}
            onChange={e => props.setTitle(e.target.value)}
            placeholder={"제목"}
        />
        <StyledTextarea
            value={props.content}
            onChange={e => props.setContent(e.target.value)}
            placeholder={"내용"}
            style={{height: WindowSize().height - 330}}
        />
        <Buttons>
          <BackButton setStep={props.setStep}
                      step={props.step}/>
          <ToStep3Button
              setStep={props.setStep}
              step={props.step}
              isWritingCompleted={(props.title.length !== 0
                  && props.content.length !== 0)}
          />
        </Buttons>
        </BoxInModal>
      </Modal>
  );
};

export default WritingStep2Modal;