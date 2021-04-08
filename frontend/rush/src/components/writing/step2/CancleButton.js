import React from 'react';
import styled from "styled-components";

const StyledCancelButton = styled.div`
  display: inline-block;
  width: 30px;
  height: 20px;
  color: #446677;
  font-size: 36px;
  font-weight: bold;
  cursor: pointer;
`;

const CancelButton = () => {
  return (
      <StyledCancelButton onClick={() => {
        if (window.confirm("hihi")) {
          window.location.href = "/";
        }
      }}>x</StyledCancelButton>
  );
};

export default CancelButton;