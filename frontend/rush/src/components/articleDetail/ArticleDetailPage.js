import React, {useEffect, useState} from 'react';
import WindowSize from "../WindowSize";
import { Outside, DisplayBox, PostBox, CommentsBox } from './Box';
import ArticleMeta from "./ArticleMeta";
import findWritingApi from "./FindWritingApi";
import ArticleBody from "./ArticleBody";
import Comment from "./Comment";
import CommentWriting from "./CommentWriting";
import findCommentsApi from "./FindCommentsApi";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import {withRouter} from "react-router-dom";

const ArticleDetailPage = (props) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const articleId = props.match.params.articleId;
  const [article, setArticle] = useState(null);
  const [comments, setComments] = useState(null);

  useEffect(() => {
    findWritingApi(articleId).then(articlePromise => {
      setArticle(articlePromise)
    })
  }, [articleId]);

  useEffect(() => {
    findCommentsApi(articleId).then(commentPromise => {
      setComments(commentPromise)
    });
    console.log(comments)
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
            <CommentWriting
                articleId={articleId}
                accessToken={accessToken}
                history={props.history}
                comments={comments}
                setComments={setComments}
            />
            {
              comments? comments.map((comment, idx) =>
                <Comment
                    key={idx}
                    content={comment.content}
                    author={comment.author}
                />
              ) : "아직 댓글이 없습니다 :)"
            }
          </CommentsBox>
        </DisplayBox>
      </Outside>
  );
};

export default withRouter(ArticleDetailPage);
