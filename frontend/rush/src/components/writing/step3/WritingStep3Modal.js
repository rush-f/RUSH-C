import React, {useState} from 'react';
import Modal from 'react-modal';
import SelectAllButton from "./SelectAllButton";
import PublicMap from "./PublicMap";
import GroupsMap from "./GroupsMap";
import PrivateMap from "./PrivateMap";

const WritingStep3Modal = (props) => {
  const groups = [
    {id: 1, name: "그룹A"},
    {id: 3, name: "그룹C"},
    {id: 4, name: "그룹D"},
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
      </Modal>
  );
};

export default WritingStep3Modal;