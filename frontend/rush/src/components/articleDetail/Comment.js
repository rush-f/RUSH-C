import React, {useEffect, useState} from 'react';
import styled from "styled-components";
import Profile from "./Profile";
import changeMyLikeInCommentIdApi from "../../api/ChangeMyLikeInCommentApi";

const CommentBox = styled.div`
  margin: 0;
  height: 100px;
  border-bottom: 2px solid rgb(90, 155, 213);
  padding: 10px;
  padding-top: 15px;
  display: flex;
  justify: center;
`;

const AuthorName = styled.div`
  font-size: 13px;
  font-weight: bold;
  margin-bottom: 3px;
`;
const CommentLike = styled.div`
  display: flex;
`
const LikeHeart = styled.div`
  font-size: 20px;
  margin-top: px;
  color: rgb(90, 155, 213);
  cursor: pointer;
  right: 0;

  //아래 5가지는 마우스 드래그를 방지한다
  -ms-user-select: none; 
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
 `

const LikeLetter = styled.div`
  padding-top:4px;
  font-size: 13px;
  margin-left:10px;
`;

const Comment = ({accessToken, comment, mapType, commentTotalLikes, hasILikedListInComment, onCommentLikeClicked,
  changetotalLikesInComment, history}) => {
  const changetotalLikes = changetotalLikesInComment.includes(comment.id) ? (hasILikedListInComment.includes(comment.id) ? 1 : -1) : 0 ;

  return (
      <CommentBox>
        <Profile imageUrl={comment.author.imageUrl}/>
        <div>
          <AuthorName>
            {comment.author.nickName}
          </AuthorName>
          <div>{comment.content}</div>
          <CommentLike>
            <LikeHeart
              onClick={() => {
                changeMyLikeInCommentIdApi(accessToken, hasILikedListInComment.includes(comment.id), comment.id, mapType,  history);
                onCommentLikeClicked(comment.id);
              }}>{hasILikedListInComment.includes(comment.id) ?"♥":"♡"} </LikeHeart>
            <LikeLetter>좋아요 { commentTotalLikes ? commentTotalLikes + changetotalLikes : 0 + changetotalLikes }개</LikeLetter>
          </CommentLike>
        </div>
      </CommentBox>
  );
};

export default Comment;