import React from 'react';
import styled from "styled-components";

const StyledCancelButton = styled.div`
  display: inline-block;
  position: absolute;
  top: 10px;
  right: 10px;
  width: 30px;
  height: 30px;
  color: #446677;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
`;

const CancelButton = () => {
  return (
      <StyledCancelButton onClick={() => {
        if (window.confirm("hihi")) {
          window.location.href = "/";
        }
      }}>X</StyledCancelButton>
  );
};

export default CancelButton;