import React, {useState} from 'react';
import Modal from "react-modal";
import CancelButton from "./CancelButton";
import GroupNameInput from "./GroupNameInput";
import CompleteButton from "./CompleteButton";

const EditNameModal = ({ isEditNameModalOpen, setIsEditNameModalOpen }) => {
  const [newGroupName, setNewGroupName] = useState("");

  return (
    <Modal
      isOpen={isEditNameModalOpen}
      shouldCloseOnOverlayClick={false}
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
      contentLabel="edit group name"
    >
      <CancelButton
        setIsEditNameModalOpen={setIsEditNameModalOpen}
        setNewGroupName={setNewGroupName}
      />
      <GroupNameInput
        maxLength='10'
        value={newGroupName}
        onChange={e => setNewGroupName(e.target.value)}
        placeholder={"바꿀 그룹 이름은?!"}
      />
      <CompleteButton
        isEditNameModalOpen={setIsEditNameModalOpen}
        newGroupName={newGroupName}
        setIsEditNameModalOpen={setIsEditNameModalOpen}
        setNewGroupName={setNewGroupName}
      />
    </Modal>
  );
};

export default EditNameModal;
