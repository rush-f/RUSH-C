import React from 'react';
import ArticleTitle from "./ArticleTitle";
import styled from "styled-components";
import addArticleLikingApi from "../../api/AddArticleLikingApi";
import deleteArticleLikingApi from "../../api/DeleteArticleLikingApi";

const ArticleContent = styled.div`
  font-size: 24px;
  margin-top: 10px;
`;

const ArticleLiking = styled.div`
  display: flex;
`;

const LikingHeart = styled.div`
  font-size: 40px;
  margin-top: 10px;
  color: rgb(90, 155, 213);
  cursor: pointer;

  //아래 5가지는 마우스 드래그를 방지한다
  -ms-user-select: none; 
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
 `;

const LikingLetter = styled.div`
  font-size: 17px;
  margin-left:7px;
  padding-top:26px;
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
        <ArticleLiking>
          <LikingHeart
            onClick={() => {
              if(props.checkMyLiking){
                deleteArticleLikingApi(props.accessToken, props.articleId, props.history);
                props.setArticleTotalLiking(props.articleTotalLiking-1);
                props.setCheckMyLiking(false);
              }
              else{
                addArticleLikingApi(props.accessToken, props.articleId, props.history);
                props.setArticleTotalLiking(props.articleTotalLiking+1);
                props.setCheckMyLiking(true);
              }
            }}>{props.checkMyLiking?"♥":"♡"} </LikingHeart>
          <LikingLetter>좋아요 {props.article ? props.articleTotalLiking : ""}개</LikingLetter>
        </ArticleLiking>
      </div>
  );
};

export default ArticleBody;