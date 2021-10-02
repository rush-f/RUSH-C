import React, {useState, useCallback, useRef} from 'react';
import styled from "styled-components";
import createCommentApi from "../../api/comment/CreateCommentApi";

const StyledDiv = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 5px;
  border-bottom: 2px solid rgb(90, 155, 213);
`;

const StyledTextarea = styled.textarea`
  display: inline-block;
  font-family: 'Gowun Dodum', sans-serif;
  font-size: 17px;
  background-color: #00000000;
  padding: 3px 10px 10px 10px;
  height: 20px;
  max-height: 80px;
  width: 100%;
  resize: none;
`;

const CommentWritingButton = styled.button`
  display: inline-block;
  height: 33px;
  width: 60px;
  margin-left: 5px;
  padding: 7px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  background: #333333;
  color: #cccccc;
  font-family: 'Gowun Dodum', sans-serif;
  border-width: 0;
  border-radius: 5px;
  border-top-left-radius: 0;
`;

const CommentWriting = ({articleId, comments, setComments, mapType, accessToken, history}) => {
  const [inputValue, setInputValue] = useState([]);
  const writingRef =useRef();

  const handleResizeHeight = useCallback(() => {
    if (writingRef === null || writingRef.current === null)
      return;
    if(writingRef.current.style.height === '20px') {
      writingRef.current.style.overflow = 'hidden';
    }
    else { writingRef.current.style.overflow= '';}
    writingRef.current.style.height = '20px';
    writingRef.current.style.height = writingRef.current.scrollHeight-18 + 'px';
  },[]);

  return (
      <StyledDiv>
        <StyledTextarea
            ref={writingRef}
            value={inputValue}
            onChange={e => {
              setInputValue(e.target.value)
              handleResizeHeight()
            }}
            placeholder="댓글을 입력해주세요 :)"
        />
        <CommentWritingButton onClick={() => {
          if(inputValue.length === 0)
            alert("댓글을 채워주세요!");
          else{
            setInputValue("");
            createCommentApi(inputValue,
                articleId,
                mapType,
                accessToken,
                history
            ).then(commentPromise => {
              setComments(new Array(commentPromise).concat(comments))
              writingRef.current.style.height = '20px';
            })
          };
        }}>
          등록
        </CommentWritingButton>
      </StyledDiv>
  );
};

export default CommentWriting;