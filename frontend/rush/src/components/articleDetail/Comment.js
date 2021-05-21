import React from 'react';
import styled from "styled-components";
import Profile from "./Profile";

const CommentBox = styled.div`
  margin: 0;
  height: 100px;
  border-bottom: 2px solid rgb(90, 155, 213);
  padding: 10px;
  display: flex;
  justify: center;
`;

const AuthorName = styled.div`
  font-size: 13px;
  font-weight: bold;
  margin-bottom: 3px;
`;

const Comment = (props) => {
  return (
      <CommentBox>
        <Profile imageUrl=""/>
        <div style={{
          paddingTop: "5px",
        }}>
          <AuthorName>
            {"김해솔"}
          </AuthorName>
          <div>{props.children}</div>
        </div>
      </CommentBox>
  );
};

export default Comment;