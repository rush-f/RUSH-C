import React from 'react';
import GroupListOpenButton from "./GroupListOpenButton";
import GroupList from "./GroupList";

const GroupsMap = (props) => {
  return (<>
        <div>그룹지도
          <GroupListOpenButton
              isGroupOpened={props.isGroupOpened}
              setIsGroupOpened={props.setIsGroupOpened}
          />
        </div>
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