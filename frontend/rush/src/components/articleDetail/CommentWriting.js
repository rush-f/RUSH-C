import React, {useState} from 'react';
import styled from "styled-components";
import createCommentApi from "../../api/CreateCommentApi";

const StyledDiv = styled.div`
  padding: 5px;
  border-bottom: 2px solid rgb(90, 155, 213);
`;

const StyledInput = styled.input`
  display: inline-block;
  font-size: 17px;
  background-color: #00000000;
  padding: 10px;
  height: 10px;
  width: auto;
`;

const CommentWritingButton = styled.button`
  display: inline-block;
  height: 27px;
  width: 50px;
  margin-left: 5px;
  padding: 7px;
`;

const CommentWriting = ({articleId, comments, setComments, mapType, accessToken, history}) => {
  const [inputValue, setInputValue] = useState([]);

  return (
      <StyledDiv>
        <StyledInput
            value={inputValue}
            onChange={e => setInputValue(e.target.value)}
            type="text"
            placeholder="댓글을 입력해주세요 :)"
        />
        <CommentWritingButton onClick={() => {
          createCommentApi(inputValue,
              articleId,
              mapType,
              accessToken,
              history
          ).then(commentPromise => {
            setComments(new Array(commentPromise).concat(comments))
          })
        }}>
          등록
        </CommentWritingButton>
      </StyledDiv>
  );
};

export default CommentWriting;