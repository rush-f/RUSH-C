import React from 'react';
import styled from "styled-components";
import Profile from "./Profile";

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
const Comment = (props) => {
  return (
      <CommentBox>
        <Profile imageUrl={props.author? props.author.imageUrl : ""}/>
        <div>
          <AuthorName>
            {props.author? props.author.nickName : ""}
          </AuthorName>
          <div>{props.content}</div>
          <CommentLike>
            <LikeHeart
              onClick={() => {
/*                props.setArticleTotalLikes(props.hasILiked?props.articleTotalLikes-1 : props.articleTotalLikes+1);
                changeMyLikeApi(props.accessToken, props.hasILiked, props.articleId, props.mapType, props.history);
                props.setHasILiked(!props.hasILiked);*/
              }
              }>{props.hasILiked?"♥":"♡"} </LikeHeart>
            <LikeLetter>좋아요 {props.commentTotalLikes >= 0 ? props.commentTotalLikes : ""}개</LikeLetter>
          </CommentLike>
        </div>
      </CommentBox>
  );
};

export default Comment;