import React from 'react';
import styled from "styled-components";
import {GROUPED} from "../../../constants/MapType";

const GroupContentStyle = styled.li`
  height: 50px;
  font-size: 110%;
  margin-bottom: 6px;
  margin-left: 20px;
  cursor: pointer;
`;

const GroupContent = ({setIsMenuOpen, groupName, groupId, setMapType, setGroupId}) => {
  const onClick = () => {
    setMapType(GROUPED);
    setGroupId(groupId);
    setIsMenuOpen(false);
  };

  return (<div>
    <GroupContentStyle onClick={() => onClick()}>
        {groupName}
      </GroupContentStyle>
  <img
      src="/groupSetting.png"
      alt="my image"
      style={{
        zIndex: 10,
        width: "20px",
        height: "20px",
        margin: "10px",
        cursor: "pointer"
      }}
      onClick={()=>{alert("Fff");}}
  /></div>
  );
};

export default GroupContent;