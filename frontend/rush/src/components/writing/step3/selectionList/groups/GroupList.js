import React, {useEffect, useState} from 'react';

import {Motion, spring} from 'react-motion';
import GroupContent from "./GroupContent";
import StyledCheckbox from "../StyledCheckbox";

const GroupList = (props) => {
  const [height, setHeight] = useState(0);

  useEffect(() => {
    setHeight(props.isGroupOpened ? (50 * props.groups.length) : 0);
  }, [props]);

  const groupsPresent = props.groups.map((e, idx) =>
      <GroupContent onClick={() => props.onGroupCheckboxClicked(props.groups[idx].id)}>
        <StyledCheckbox
               onChange={() => props.onGroupCheckboxClicked(props.groups[idx].id)}
               checked={props.checkedGroups.includes(props.groups[idx].id)}/>
        {props.groups[idx].name}
      </GroupContent>
  );

  return (
      <div className="App">
        <Motion style={{height: spring(height)}}>
        {
          ({height}) => <div style={Object.assign({}, styles.menu, {height})}>
            {groupsPresent}
          </div>
        }
        </Motion>
      </div>
  );
}

const styles = {
  menu: {
    overflow: 'hidden',
  }
}

export default GroupList;