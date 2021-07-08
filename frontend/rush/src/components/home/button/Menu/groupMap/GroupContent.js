import React from 'react';
import styled from "styled-components";
import {GROUPED} from "../../../../../constants/MapType";

const GroupContentStyle = styled.li`
  height: 50px;
  font-size: 110%;
  margin-bottom: 6px;
  margin-left: 20px;
  cursor: pointer;
`;

const GroupContent = ({groupName, groupId, setMapType, setGroupId}) => {
  const onClick = () => {
    setMapType(GROUPED);
    setGroupId(groupId);
  };

  return (
      <GroupContentStyle onClick={() => onClick()}>
        {groupName}
      </GroupContentStyle>
  );
};

export default GroupContent;