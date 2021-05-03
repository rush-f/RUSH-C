import React from 'react';
import Profile from "./Profile";
import styled from "styled-components";
import CancelButton from "../writing/CancelButton";
import CreateDate from "./CreateDate";

const StyledDiv = styled.div`
  border: 1px solid red;
  display: flex;
  justify: center;
`;

const Name = styled.div`
  font-size: 20px
`;

const ArticleMeta = (props) => {
  return (<>
    <CancelButton />
    <StyledDiv>
      <Profile />
      <div style={{
        paddingTop: "5px",
        border: "1px solid red",
      }}>
        <Name>
          {props.author.name}
        </Name>
        <CreateDate iso8601format={props.createDate}/>
      </div>
    </StyledDiv>
  </>);
};

export default ArticleMeta;