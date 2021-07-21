import React, {useEffect, useState} from 'react';
import WindowSize from "../WindowSize";
import {CommentsBox, DisplayBox, Outside, PostBox} from '../common/Box';
import ArticleMeta from "./ArticleMeta";
import findWritingApi from "../../api/FindWritingApi";
import ArticleBody from "./ArticleBody";
import Comment from "./Comment";
import CommentWriting from "./CommentWriting";
import findCommentsApi from "../../api/FindCommentsApi";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import {withRouter} from "react-router-dom";
import {GROUPED, PRIVATE, PUBLIC} from "../../constants/MapType";
import checkArticleMyLikingApi from "../../api/CheckArticleMyLikingApi";

const ArticleDetailPage = (props) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const articleId = props.match.params.articleId;
  const mapType = props.match.params.mapType;
  const [article, setArticle] = useState(null);
  const [comments, setComments] = useState(null);
  const [MyLiking,setMyLiking] = useState(false);
  const [articleTotalLiking, setArticleTotalLiking] = useState(0);

  useEffect(() => {
    if (mapType === GROUPED || mapType === PUBLIC || mapType === PRIVATE) {
      findWritingApi(articleId, mapType, props.history).then(articlePromise => {
        setArticle(articlePromise);
        setArticleTotalLiking(articlePromise.totalLiking);
      })
    }
    else {
      alert("주소가 올바르지 않습니다.");
      props.history.push("/");
    }
  }, [articleId, mapType]);

  useEffect(()=>{
    if(accessToken)
    checkArticleMyLikingApi(accessToken, articleId).then(checkLiking =>{
      setMyLiking(checkLiking);
    })
  },[MyLiking]);

  useEffect(() => {
    findCommentsApi(articleId).then(commentPromise => {
      setComments(commentPromise)
    });
  }, [articleId]);

  return (
    <Outside>
      <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
        <PostBox>
          <ArticleMeta
            author={article ? {
              nickName: article.author ? article.author.nickName : "",
              imageUrl: article.author ? article.author.imageUrl : ""
            } : {
              nickName: "",
              imageUrl: ""
            }}
            createDate={article ? article.createDate : ""}
            markerLat={article ? article.latitude : ""}
            markerLng={article ? article.longitude : ""}
          />
          <ArticleBody
            accessToken={accessToken}
            articleId={articleId}
            article={article}
            articleTotalLiking={articleTotalLiking}
            setArticleTotalLiking={setArticleTotalLiking}
            checkMyLiking={MyLiking}
            setCheckMyLiking={setMyLiking}
            history={props.history}
          />
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
            comments ? comments.map((comment, idx) =>
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
