import React, {useEffect, useState} from 'react';
import {Motion, spring} from "react-motion";
import GroupContent from "./GroupContent";


const GroupsList = (props) => {
  const [height, setHeight] = useState(0);

  useEffect(() => {
    setHeight(props.isOpened ? (50 * props.myGroups.length) : 0);
  }, [props]);

  const groupPresent = props.myGroups ? props.myGroups.map((group) =>
      <GroupContent
          group={group}
          hitsory={props.history}
      />
  ) : "";

  const styles = {
    menu: {
      overflow: 'hidden',
    }
  };

  return (
      <div className="App">
        <Motion style={{height: spring(height)}}>
          {
            ({height}) => <div style={Object.assign({}, styles.menu, {height})}>
              {groupPresent}
            </div>
          }
        </Motion>
      </div>
  );
};

export default GroupsList;