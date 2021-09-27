import React from 'react';
import ArticleTitle from "./ArticleTitle";
import styled from "styled-components";
import changeMyLikeApi from "../../api/article/ChangeMyLikeApi";
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

const ArticleBottom = styled.div`
  position: relative;
`;

const MyArticleControl = styled.div`
  min-width: 70px;
  max-width: 140px;
  height: 100%;
  position: absolute;
  right: 0;
  bottom: 0;
  text-align: right;
`;

const MyArticleControlInner = styled.div`
  height: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-end;
`;

const ArticleBody = (props) => {
  return (
      <div>
        <ArticleTitle>
          {props.article ? props.article.title : ""}
        </ArticleTitle>
        <ArticleContent>
          {props.article ? props.article.content.split('\n').map((line) => {
            return (<>{line}<br/></>)
          }) : ""}
        </ArticleContent>
        <ArticleBottom>
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
          {
            props.isMyArticle? <MyArticleControl>
              <MyArticleControlInner>
                <DeleteButton
                  articleId={props.articleId}
                  accessToken={props.accessToken}
                  markerLat={props.article.latitude}
                  markerLng={props.article.longitude}
                  history={props.history}
                />
              </MyArticleControlInner>
            </MyArticleControl> : ""
          }
        </ArticleBottom>
      </div>
  );
};

export default ArticleBody;