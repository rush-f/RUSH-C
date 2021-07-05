import React, {useEffect, useState} from 'react';

import {Motion, spring} from 'react-motion';
import GroupContent from "./GroupContent";
import findMyGroupsApi from "../../../../api/FindMyGroupsApi";
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
    if (props.isGroupOpened) {
      findMyGroupsApi(props.history).then(groupsPromise => {
        setGroups(groupsPromise)
      }).catch(() => {
        alert("로그인이 필요한 서비스입니다.")
        props.history.push('/login');
      })
    }
  }, [props.isGroupOpened]);

  useEffect(() => {
    setHeight(props.isGroupOpened ? (50 * (groups.length + 2) + 30) : 0);
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
            <GroupSetting onClick={() => props.setIsCreateGroupModalOpen(true)}>
              그룹 만들기
            </GroupSetting>
            <GroupSetting>그룹 가입하기</GroupSetting>
          </div>
        }
      </Motion>
    </div>
  );
};

export default GroupList;
