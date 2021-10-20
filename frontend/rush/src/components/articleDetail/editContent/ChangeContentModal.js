import React, {useState} from 'react';
import Modal from "react-modal";
import CancelButton from "./CancelButton";
import NewContentTextarea from "./NewContentTextarea";
import CompleteButton from "./CompleteButton";

const ChangeContentModal = ({
  isChangeContentModalOpened,
  setIsChangeContentModalOpened,
  accessToken,
  articleId,
  history
}) => {
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
      <NewContentTextarea
        value={newContent}
        onChange={e => setNewContent(e.target.value)}
        placeholder={"내용을 작성해주세요"}
      />
      <CompleteButton
          isChangeContentModalOpened={isChangeContentModalOpened}
          newContent={newContent}
          accessToken={accessToken}
          articleId={articleId}
          history={history}
      />
    </Modal>
  );
};

export default ChangeContentModal;