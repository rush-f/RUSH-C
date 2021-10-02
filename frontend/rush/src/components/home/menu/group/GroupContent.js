import React from 'react';
import styled from "styled-components";
import {GROUPED} from "../../../../constants/MapType";

const StyledDiv = styled.div`
  position: relative;
  height: 50px;
  margin-bottom: 6px;
  margin-left: 20px;
`;
const GroupContentStyle = styled.li` 
  position: absolute;
  font-size: 110%;
  cursor: pointer;
`;

const GroupContent = ({setIsMenuOpen, groupName, groupId, setMapType, setGroupId, history}) => {
  const onClick = () => {
    setMapType(GROUPED);
    setGroupId(groupId);
    setIsMenuOpen(false);
  };

  return (<StyledDiv>
    <GroupContentStyle onClick={() => onClick()}>
        {groupName}
      </GroupContentStyle>
  <img
      src="/groupSetting.png"
      alt="my image"
      style={{
        position: "absolute",
        zIndex: 10,
        right: "10px",
        width: "40px",
        height: "20px",
        margin: "0 -20px 0 0",
        padding: "5px 10px 5px 10px",
        cursor: "pointer",
      }}
      onClick={()=>history.push("/groups/" + groupId)}
  /></StyledDiv>
  );
};

export default GroupContent;