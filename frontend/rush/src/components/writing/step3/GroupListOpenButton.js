import React from 'react';
import styled from "styled-components";

const StyledDiv = styled.div`
  display: inline-block;
`;

const GroupListOpenButton = (props) => {
  return (
      <StyledDiv onClick={() => props.setIsGroupOpened(!props.isGroupOpened)}>
        {props.isGroupOpened? "▲" : "▼"}
      </StyledDiv>
  );
};

export default GroupListOpenButton;