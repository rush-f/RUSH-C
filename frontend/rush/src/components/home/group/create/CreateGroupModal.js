import React, {useState} from 'react';
import Modal from "react-modal";
import WindowSize from "../../../WindowSize";
import CancelButton from "./CancelButton";
import GroupNameInput from "./GroupNameInput";
import CompleteButton from "./CompleteButton";

const CreateGroupModal = ({isCreateGroupModalOpen, setIsCreateGroupModalOpen}) => {
  const [groupName, setGroupName] = useState("");

  return (
    <Modal
      isOpen={isCreateGroupModalOpen}        //모달의 오픈유무를 bool값으로 정한다.
      shouldCloseOnOverlayClick={false}     //close버튼을 눌러야만 모달이 종료
      style={{
        overlay: {
          backgroundColor: '#00112255'
        },
        content: {
          maxWidth: '500px',
          margin: 'auto',
          backgroundColor: 'rgb(222, 235, 247)',
        }
      }}
      contentLabel="create group"       //모달의 라벨
    >
      <CancelButton
        setIsCreateGroupModalOpen={setIsCreateGroupModalOpen}
        setGroupName={setGroupName}
      />
      <GroupNameInput
        value={groupName}
        onChange={e => setGroupName(e.target.value)}
        placeholder={"그룹이름"}
        style={{height: WindowSize().height - 300}}
      />
      <CompleteButton
        isCreateGroupModalOpen={setIsCreateGroupModalOpen}
        groupName={groupName}
      />
    </Modal>
  );
};

export default CreateGroupModal;