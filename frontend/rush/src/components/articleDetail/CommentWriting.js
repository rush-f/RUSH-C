import React, {useState} from 'react';
import styled from "styled-components";
import createCommentApi from "../../api/CreateCommentApi";

const StyledDiv = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 5px;
  border-bottom: 2px solid rgb(90, 155, 213);
`;

const StyledInput = styled.input`
  display: inline-block;
  font-family: 'Gowun Dodum', sans-serif;
  font-size: 17px;
  background-color: #00000000;
  padding: 10px;
  height: 10px;
  width: 100%;
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

const CommentWriting = ({articleId, comments, setComments, mapType, accessToken, history, updatePage, setUpdatePage}) => {
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
            })
          };
          setUpdatePage(!updatePage);
        }}>
          등록
        </CommentWritingButton>
      </StyledDiv>
  );
};

export default CommentWriting;