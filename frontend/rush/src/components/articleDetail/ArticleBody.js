import React from 'react';
import ArticleTitle from "./ArticleTitle";
import styled from "styled-components";
import changeMyLikeApi from "../../api/ChangeMyLikeApi";
import DeleteButton from "./DeleteButton";

const ArticleContent = styled.div`
  font-size: 24px;
  margin-top: 10px;
`

const ArticleLike = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`

const LikeHeart = styled.div`
  font-size: 40px;
  color: rgb(90, 155, 213);
  cursor: pointer;

  //아래 5가지는 마우스 드래그를 방지한다
  -ms-user-select: none; 
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
`

const LikeLetter = styled.div`
  font-size: 17px;
  margin-left: 7px;
`;

const ArticleMetaBottom = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const ArticleBody = (props) => {
  return (
      <div>
        <ArticleTitle>
          {props.article ? props.article.title : ""}
        </ArticleTitle>
        <ArticleContent>
          {props.article ? props.article.content : ""}
        </ArticleContent>
        <ArticleMetaBottom>
          <ArticleLike>
            <LikeHeart
              onClick={() => {
                props.setArticleTotalLikes(props.hasILiked?props.articleTotalLikes-1 : props.articleTotalLikes+1);
                changeMyLikeApi(props.accessToken, props.hasILiked, props.articleId, props.mapType, props.history);
                props.setHasILiked(!props.hasILiked);
                }
              }>{props.hasILiked?"♥":"♡"} </LikeHeart>
            <LikeLetter>좋아요 {props.article ? props.articleTotalLikes : ""}개</LikeLetter>
          </ArticleLike>
          <DeleteButton/>
        </ArticleMetaBottom>
      </div>
  );
};

export default ArticleBody;