import React from 'react';
import styled from 'styled-components';
import WindowSize from "../WindowSize";

const Outside = styled.div`
  max-width: 400px;
  margin: auto;
`;

const DisplayBox = styled.div`
  margin: 10px;
  border: 2px solid rgb(90, 155, 213);
  border-radius: 10px;
  overflow-y: scroll;
`;

const PostBox = styled.div`
  margin: 0;
  border-bottom: 2px solid rgb(90, 155, 213);
  padding: 10px;
`;

const CommentsBox = styled.div`
  margin: 0;
`;

const CommentBox = styled.div`
  margin: 0;
  height: 100px;
  border-bottom: 2px solid rgb(90, 155, 213);
  padding: 10px;
`;

const PostDetailPage = (props) => {
  const postId = props.match.params.postId;

  return (
      <Outside>
        <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
          <PostBox>
            홍길동<br/><br/><br/><br/><br/><br/>
            {postId}<br/><br/><br/><br/>
          </PostBox>
          <CommentsBox>
            <CommentBox>1</CommentBox>
            <CommentBox>2</CommentBox>
            <CommentBox>3</CommentBox>
            <CommentBox>4</CommentBox>
            <CommentBox>5</CommentBox>
            <CommentBox>6</CommentBox>
            <CommentBox>7</CommentBox>
            <CommentBox>8</CommentBox>
          </CommentsBox>
        </DisplayBox>
      </Outside>
  );
};

export default PostDetailPage;