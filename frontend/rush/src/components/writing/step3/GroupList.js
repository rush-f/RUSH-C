import React, {useEffect, useState} from 'react';

import {Motion, spring} from 'react-motion';
import GroupContent from "./GroupContent";

const GroupList = (props) => {
  const [height, setHeight] = useState(233);

  useEffect(() => {
    setHeight(props.isGroupOpened? 233 : 0);
  },[props]);

  return (
      <div className="App">
        <Motion style={{height: spring(height)}}>
          {
            ({height}) => <div style={Object.assign({}, styles.menu, {height})}>
              <GroupContent><input type="checkbox"/>하이하이</GroupContent>
              <GroupContent><input type="checkbox"/>하이하이</GroupContent>
              <GroupContent><input type="checkbox"/>하이하이</GroupContent>
              <GroupContent><input type="checkbox"/>하이하이</GroupContent>
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