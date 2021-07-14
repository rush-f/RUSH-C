import React from 'react';
import styled from "styled-components";

const StyledDiv = styled.div`
  display: inline-block;
`;

const GroupsListOpenButton = (props) => {
  return (
      <StyledDiv onClick={() => props.setIsOpened(!props.isOpened)}>
        {props.isOpened? "▲" : "▼"}
      </StyledDiv>
  );
};

export default GroupsListOpenButton;