import React from 'react';
import styled from "styled-components";
import ImportantStart from "./ImportantStart";

const Box = styled.div`
  margin-bottom: 15px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
`;

const GroupNameStyle = styled.div`
  font-size: 23px;
  text-align: center;
`;

const EditGroupNameButton = styled.div`
  font-size: 12px;
  color: #777777;
  cursor: pointer;
`;

const GroupHead = ({groupName, setIsEditNameModalOpen, important}) => {
  return (
    <Box>
      <GroupNameStyle>{groupName}</GroupNameStyle>
      <EditGroupNameButton onClick={() => setIsEditNameModalOpen(true)}>
        edit
      </EditGroupNameButton>
      <ImportantStart important={important} />
    </Box>
  );
};

export default GroupHead;
