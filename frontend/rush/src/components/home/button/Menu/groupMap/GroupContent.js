import React from 'react';
import styled from "styled-components";

const GroupContentStyle = styled.li`
  height: 50px;
  font-size: 110%;
  margin-bottom: 6px;
  margin-left: 20px;
  cursor: pointer;
`;

const GroupContent = (props) => {
  return (
      <GroupContentStyle onClick={() => alert("아직 개발중입니다!")}>
        {props.groupName}
      </GroupContentStyle>
  );
};

export default GroupContent;