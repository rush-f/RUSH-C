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

  const [isPublicMapChecked, setIsPublicMapChecked] = useState(false);
  const [isPrivateMapChecked, setIsPrivateMapChecked] = useState(false);
  const [checkedGroups, setCheckedGroups] = useState([]);

  const onGroupCheckboxClicked = (groupId) => {
    if (checkedGroups.includes(groupId)) {
      setCheckedGroups(checkedGroups.filter(e => e !== groupId))
    } else {
      setCheckedGroups([...checkedGroups, groupId]);
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
            isPublicMapChecked={isPublicMapChecked}
            isPrivateMapChecked={isPrivateMapChecked}
            checkedGroups={checkedGroups}
            setIsPublicMapChecked={setIsPublicMapChecked}
            setIsPrivateMapChecked={setIsPrivateMapChecked}
            setCheckedGroups={setCheckedGroups}
            groups={groups}
        />
        <PublicMap
            isPublicMapChecked={isPublicMapChecked}
            setIsPublicMapChecked={setIsPublicMapChecked}
        />
        <GroupsMap
            groups={groups}
            checkedGroups={checkedGroups}
            isGroupOpened={isGroupOpened}
            setIsGroupOpened={setIsGroupOpened}
            onGroupCheckboxClicked={onGroupCheckboxClicked}
        />
        <PrivateMap
            isPrivateMapChecked={isPrivateMapChecked}
            setIsPrivateMapChecked={setIsPrivateMapChecked}
        />
        </StyledDiv>
        <BackButton step={props.step} setStep={props.setStep}/>
        <CompleteButton
            step={props.step}
            title={props.title}
            content={props.content}
            center={props.center}
        />
      </Modal>
  );
};

export default WritingStep3Modal;