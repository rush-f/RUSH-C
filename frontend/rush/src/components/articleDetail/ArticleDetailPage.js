import React, {useEffect, useState} from 'react';
import WindowSize from "../WindowSize";
import { Outside, DisplayBox, PostBox, CommentsBox } from './Box';
import ArticleMeta from "./ArticleMeta";
import findWritingApi from "./FindWritingApi";
import ArticleBody from "./ArticleBody";
import Comment from "./Comment";

const ArticleDetailPage = (props) => {
  const articleId = props.match.params.articleId;
  const [article, setArticle] = useState(null);

  useEffect(() => {
    findWritingApi(articleId).then(articlePromise => {
      setArticle(articlePromise)
    })
  }, [articleId]);

  return (
      <Outside>
        <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
          <PostBox>
            <ArticleMeta
                author={article? {
                  nickName: article.author? article.author.nickName : "",
                  imageUrl: article.author? article.author.imageUrl : ""
                } : {
                  nickName: "",
                  imageUrl: ""
                }}
                createDate={article? article.createDate:""}
            />
            <ArticleBody article={article}/>
          </PostBox>
          <CommentsBox>
            <Comment>우와 멋있어요~~~~</Comment>
            <Comment>안녕하세요 ㅎㅎ</Comment>
            <Comment>☆🤍🤍ㅁ🅱🅱🆎</Comment>
            <Comment>댓글5</Comment>
            <Comment>댓글6</Comment>
            <Comment>댓글7</Comment>
            <Comment>댓글8</Comment>
          </CommentsBox>
        </DisplayBox>
      </Outside>
  );
};

export default ArticleDetailPage;
