import React from 'react';
import styled from "styled-components";
import {Email, Name} from "./Box";
import CreateDate from "../../../../../util/CreateDate";

const StyledInfo = styled.div`
  padding-Top: 2px;
  margin-left:20px
`;
const Info = (props) => {
  return (
      <StyledInfo>
        <Name>
          {props.user.nickName}
        </Name>
        <Email>
          {props.user.email}
        </Email>
        <CreateDate iso8601format={props.user.visitDate}/>
      </StyledInfo>
  );
};

export default Info;