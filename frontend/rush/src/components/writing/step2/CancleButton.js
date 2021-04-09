import React from 'react';
import styled from "styled-components";
import {withRouter} from "react-router-dom";

const StyledCancelButton = styled.div`
  display: inline-block;
  width: 30px;
  height: 20px;
  color: #446677;
  font-size: 36px;
  font-weight: bold;
  cursor: pointer;
`;

const CancelButton = ({history}) => {
  return (
      <StyledCancelButton onClick={() => {
        if (window.confirm("hihi")) {
          history.push("/");
        }
      }}>x</StyledCancelButton>
  );
};

export default withRouter(CancelButton);