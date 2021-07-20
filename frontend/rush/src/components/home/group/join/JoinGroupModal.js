import React, {useState} from 'react';
import Modal from "react-modal";
import CancelButton from "./CancelButton";
import InvitationCodeInput from "./InvitationCodeInput";
import CompleteButton from "./CompleteButton";
import {withRouter} from "react-router-dom";

const JoinGroupModal = ({isJoinGroupModalOpen, setIsJoinGroupModalOpen, setIsGroupOpened, history}) => {
  const [invitationCode, setInvitationCode] = useState("");

  return (
    <Modal
      isOpen={isJoinGroupModalOpen}        // 모달의 오픈유무를 bool값으로 정한다.
      shouldCloseOnOverlayClick={false}      // close버튼을 눌러야만 모달이 종료
      style={{
        overlay: {
          backgroundColor: '#00112255'
        },
        content: {
          maxWidth: '500px',
          margin: 'auto',
          backgroundColor: 'rgb(222, 235, 247)',
          maxHeight: '300px',
          textAlign: 'center'
        }
      }}
      contentLabel="create group"       // 모달의 라벨
    >
      <CancelButton
        setIsCreateGroupModalOpen={setIsJoinGroupModalOpen}
        setGroupName={setInvitationCode}
        setIsGroupOpened={setIsGroupOpened}
      />
      <InvitationCodeInput
        value={invitationCode}
        onChange={e => setInvitationCode(e.target.value)}
        placeholder={"초대코드"}
      />
      <CompleteButton
        isCreateGroupModalOpen={setIsJoinGroupModalOpen}
        invitationCode={invitationCode}
        history={history}
      />
    </Modal>
  );
};

export default withRouter(JoinGroupModal);
