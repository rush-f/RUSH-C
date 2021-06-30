import React, {useEffect, useState} from 'react';

import {Motion, spring} from 'react-motion';
import GroupContent from "./GroupContent";
import findMyGroupsApi from "../../FindMyGroupsApi";
import styled from "styled-components";

const GroupSetting = styled.li`
  height: 50px;
  font-size: 110%;
  margin-bottom: 6px;
  margin-left: 20px;
  cursor: pointer;
  color: #888888;
`;

const styles = {
  menu: {
    overflow: 'hidden',
  }
};

const GroupList = (props) => {
  const [height, setHeight] = useState(50);
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    findMyGroupsApi(props.history).then(groupsPromise => {
      setGroups(groupsPromise)
    });
  }, []);

  useEffect(() => {
    setHeight(props.isGroupOpened ? (50 * (groups.length + 2)) : 0);
  }, [props.isGroupOpened, groups]);

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
            <GroupSetting>그룹 만들기</GroupSetting>
            <GroupSetting>그룹 가입하기</GroupSetting>
          </div>
        }
      </Motion>
    </div>
  );
};

export default GroupList;
