import React from 'react';
import GroupListOpenButton from "./GroupListOpenButton";
import GroupList from "./GroupList";
import StyledElement from "../StyledElement";

const GroupsMap = (props) => {
  return (<>
        <StyledElement
            onClick={() => props.setIsGroupOpened(!props.isGroupOpened)}
            style={{marginTop: '19px'}}
        >
          그룹지도
          <GroupListOpenButton
              isGroupOpened={props.isGroupOpened}
              setIsGroupOpened={props.setIsGroupOpened}
          />
        </StyledElement>
        <GroupList
            isGroupOpened={props.isGroupOpened}
            groups={props.groups}
            checkedGroups={props.checkedGroups}
            onGroupCheckboxClicked={props.onGroupCheckboxClicked}
        />
      </>
  );
};

export default GroupsMap;