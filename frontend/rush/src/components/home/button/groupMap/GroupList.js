import React, {useEffect, useState} from 'react';

import {Motion, spring} from 'react-motion';
import GroupContent from "./GroupContent";
import findMyGroupsApi from "../../FindMyGroupsApi";

const GroupList = (props) => {
  const [height, setHeight] = useState(233);
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    setHeight(props.isGroupOpened ? 233 : 0);
  }, [props.isGroupOpened]);

  useEffect(() => {
    findMyGroupsApi(props.history).then(groupsPromise => {
      setGroups(groupsPromise)
    });
  }, []);

  return (
    <div className="App">
      <Motion style={{height: spring(height)}}>
        {
          ({height}) => <div style={Object.assign({}, styles.menu, {height})}>
            {
              groups ? groups.map(group =>
                <GroupContent
                  groupName={group.name}
                  groupId={group.id}
                />
              ) : []
            }
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