import React, {useEffect, useState} from 'react';

import {Motion, spring} from 'react-motion';
import GroupContent from "./GroupContent";

const GroupList = (props) => {
  const [height, setHeight] = useState(233);

  useEffect(() => {
    setHeight(props.isGroupOpened ? 233 : 0);
  }, [props]);

  const groupsPresent = props.groups.map((e, idx) =>
      <GroupContent>
        <input type="checkbox"
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