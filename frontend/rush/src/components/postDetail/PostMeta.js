import React from 'react';
import Profile from "./Profile";
import styled from "styled-components";
import CancelButton from "../writing/CancelButton";

const StyledDiv = styled.div`
  border: 1px solid red;
`;

const PostMeta = (props) => {
  return (<>
    <CancelButton />
    <StyledDiv>
      <Profile />
      {props.author.name}
    </StyledDiv>
  </>);
};

export default PostMeta;