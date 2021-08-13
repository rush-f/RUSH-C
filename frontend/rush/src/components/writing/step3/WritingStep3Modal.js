import React, {useEffect, useState} from 'react';
import Modal from 'react-modal';
import SelectAllButton from "./SelectAllButton";
import PublicMap from "./selectionList/PublicMap";
import GroupsMap from "./selectionList/groups/GroupsMap";
import PrivateMap from "./selectionList/PrivateMap";
import CancelButton from "../CancelButton";
import styled from "styled-components";
import CompleteButton from "./CompleteButton";
import BackButton from "./BackButton";
import WindowSize from "../../WindowSize";
import findMyGroupsApi from "../../../api/FindMyGroupsApi";

const StyledDiv = styled.div`
  padding: 15px;
  border: 2px solid rgb(90, 155, 213);
  overflow-y: scroll;
`;

const WritingStep3Modal = (props) => {
  const [isGroupOpened, setIsGroupOpened] = useState(false);
  const [groups, setGroups] = useState([]);

  useEffect(() => {
      findMyGroupsApi(props.history).then(groupsPromise => {
        setGroups(groupsPromise)
      });
  }, []);

  const onGroupCheckboxClicked = (groupId) => {
    if (props.checkedGroupIds.includes(groupId)) {
      props.setCheckedGroups(props.checkedGroupIds.filter(e => e !== groupId))
    } else {
      props.setCheckedGroups([...props.checkedGroupIds, groupId]);
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
              backgroundColor: 'white',
            }
          }}
          contentLabel="Example Modal"
      >
        <CancelButton />
        <StyledDiv style={{height: WindowSize().height - 250}}>
        <SelectAllButton
            isPublicMapChecked={props.isPublicMapChecked}
            isPrivateMapChecked={props.isPrivateMapChecked}
            checkedGroups={props.checkedGroupIds}
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
            checkedGroups={props.checkedGroupIds}
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
            groupIds={props.checkedGroupIds}
        />
      </Modal>
  );
};

export default WritingStep3Modal;
