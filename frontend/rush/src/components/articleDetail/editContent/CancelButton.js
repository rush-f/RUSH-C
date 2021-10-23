import React from 'react';
import styled from "styled-components";

const StyledCancelButton = styled.div`
  display: inline-block;
  position: absolute;
  top: -15px;
  right: -5px;
  width: 30px;
  height: 36px;
  color: #446677;
  font-size: 36px;
  font-weight: bold;
  cursor: pointer;
`;

const CancelButton = ({setIsChangeContentModalOpened, setNewContent}) => {
  return (<div style={{ position: "relative", width: "100%", height: "36px"}}>
      <StyledCancelButton onClick={() => {
        setIsChangeContentModalOpened(false);
        setNewContent("");
      }}>x</StyledCancelButton>
    </div>
  );
};

export default CancelButton;