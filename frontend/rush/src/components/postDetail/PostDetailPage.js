import React from 'react';
import WindowSize from "../WindowSize";
import { Outside, DisplayBox, PostBox, CommentsBox, CommentBox } from './Box';
import PostMeta from "./PostMeta";

const PostDetailPage = (props) => {
  const postId = props.match.params.postId;

  const post = {
    title: "여기 완전 맛있음!!",
    content: "여기 완전 맛집임\nOO식당",
    author: {
      name: "홍길asa동",
      imageUrl: ""
    },
    date: [ 2021, 9, 2 ],
    likeCount: 10
  };

  return (
      <Outside>
        <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
          <PostBox>
            <PostMeta author={post.author}/>
            <br/><br/>
            {postId}<br/><br/><br/><br/>
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

export default PostDetailPage;