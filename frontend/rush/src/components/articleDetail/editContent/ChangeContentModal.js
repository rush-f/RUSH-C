import React, {useState} from 'react';
import Modal from "react-modal";
import CancelButton from "./CancelButton";

const ChangeContentModal = ({isChangeContentModalOpened, setIsChangeContentModalOpened, accessToken, articleId}) => {
  const [newContent, setNewContent] = useState("");

  return (
    <Modal
      isOpen={isChangeContentModalOpened}        //모달의 오픈유무를 bool값으로 정한다.
      shouldCloseOnOverlayClick={false}     //close버튼을 눌러야만 모달이 종료
      style={{
        overlay: {
          backgroundColor: '#00112255'
        },
        content: {
          maxWidth: '500px',
          margin: 'auto',
          backgroundColor: 'white',
          maxHeight: '300px',
          textAlign: 'center'
        }
      }}
      contentLabel="change content"       //모달의 라벨
    >
      <CancelButton
        setIsChangeContentModalOpened={setIsChangeContentModalOpened}
        setNewContent={setNewContent}
      />
    </Modal>
  );
};

export default ChangeContentModal;