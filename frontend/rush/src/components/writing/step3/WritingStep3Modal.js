import React, {useState} from 'react';
import Modal from 'react-modal';
import SelectAllButton from "./SelectAllButton";
import PublicMap from "./selectionList/PublicMap";
import GroupsMap from "./selectionList/groupsMap/GroupsMap";
import PrivateMap from "./selectionList/PrivateMap";
import CancelButton from "../CancelButton";
import styled from "styled-components";
import CompleteButton from "./CompleteButton";
import BackButton from "./BackButton";
import WindowSize from "../../WindowSize";

const StyledDiv = styled.div`
  padding: 15px;
  border: 2px solid rgb(90, 155, 213);
  overflow-y: scroll;
`;

const WritingStep3Modal = (props) => {
  const groups = [
    {id: 1, name: "그룹A"},
    {id: 3, name: "그룹C"},
    {id: 4, name: "그룹D"},
    {id: 5, name: "그룹D"},
    {id: 6, name: "그룹D"},
    {id: 7, name: "그룹D"},
    {id: 8, name: "그룹D"},
    {id: 9, name: "그룹D"},
    {id: 11, name: "그룹D"},
    {id: 24, name: "그룹D"},
  ];
  const [isGroupOpened, setIsGroupOpened] = useState(false);

  const onGroupCheckboxClicked = (groupId) => {
    if (props.checkedGroups.includes(groupId)) {
      props.setCheckedGroups(props.checkedGroups.filter(e => e !== groupId))
    } else {
      props.setCheckedGroups([...props.checkedGroups, groupId]);
    }
  };

  return (
      <Modal
          isOpen={props.step === 3}
          shouldCloseOnOverlayClick={false}
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
          contentLabel="Example Modal"
      >
        <CancelButton />
        <StyledDiv style={{height: WindowSize().height - 250}}>
        <SelectAllButton
            isPublicMapChecked={props.isPublicMapChecked}
            isPrivateMapChecked={props.isPrivateMapChecked}
            checkedGroups={props.checkedGroups}
            setIsPublicMapChecked={props.setIsPublicMapChecked}
            setIsPrivateMapChecked={props.setIsPrivateMapChecked}
            setCheckedGroups={props.setCheckedGroups}
            groups={groups}
        />
        <PublicMap
            isPublicMapChecked={props.isPublicMapChecked}
            setIsPublicMapChecked={props.setIsPublicMapChecked}
        />
        <GroupsMap
            groups={groups}
            checkedGroups={props.checkedGroups}
            isGroupOpened={isGroupOpened}
            setIsGroupOpened={setIsGroupOpened}
            onGroupCheckboxClicked={onGroupCheckboxClicked}
        />
        <PrivateMap
            isPrivateMapChecked={props.isPrivateMapChecked}
            setIsPrivateMapChecked={props.setIsPrivateMapChecked}
        />
        </StyledDiv>
        <BackButton step={props.step} setStep={props.setStep}/>
        <CompleteButton
            step={props.step}
            title={props.title}
            content={props.content}
            center={props.center}
            publicMap={props.isPublicMapChecked}
            privateMap={props.isPrivateMapChecked}
            groups={props.checkedGroups}
        />
      </Modal>
  );
};

export default WritingStep3Modal;