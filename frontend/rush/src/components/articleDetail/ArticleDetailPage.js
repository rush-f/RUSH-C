import React, {useEffect, useState} from 'react';
import WindowSize from "../WindowSize";
import { Outside, DisplayBox, PostBox, CommentsBox, CommentBox } from './Box';
import ArticleMeta from "./ArticleMeta";
import findWritingApi from "./FindWritingApi";
import ArticleBody from "./ArticleBody";

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

export default ArticleDetailPage;
