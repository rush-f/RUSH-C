import React from 'react';
import styled from "styled-components";

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
`;

const GroupName = ({groupName, setIsEditNameModalOpen}) => {
  return (
    <Box>
      <GroupNameStyle>{groupName}</GroupNameStyle>
      <EditGroupNameButton onClick={() => setIsEditNameModalOpen(true)}>
        edit
      </EditGroupNameButton>
    </Box>
  );
};

export default GroupName;