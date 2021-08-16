import React from 'react';
import styled from "styled-components";
import Profile from "./Profile";
import changeMyLikeInCommentIdApi from "../../api/ChangeMyLikeInCommentApi";
import deleteCommentApi from "../../api/DeleteCommentApi";

const CommentBox = styled.div`
  margin: 0;
  height: 100px;
  border-bottom: 2px solid rgb(90, 155, 213);
  padding: 15px 10px 0 10px;
  display: flex;
  justify: center;
`;

const CommentRight = styled.div`
  width: 80%;
`;

const AuthorName = styled.div`
  font-size: 13px;
  font-weight: bold;
  margin-bottom: 3px;
`;

const CommentBottom = styled.div`
  width: 100%;
  height: 35px;
  position: relative;
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const CommentLike = styled.div`
  position: absolute;
  left: 0;
`;

const CommentLikeInner = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const LikeHeart = styled.div`
  display: inline-block;
  font-size: 20px;
  color: rgb(90, 155, 213);
  cursor: pointer;

  //아래 5가지는 마우스 드래그를 방지한다
  -ms-user-select: none; 
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
`;

const LikeLetter = styled.div`
  display: inline-block;
  font-size: 13px;
  margin-left: 10px;
`;

const MyCommentControl = styled.div`
  position: absolute;
  right: 0;
`;

const MyCommentControlInner = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-end;
`;

const DeleteComment = styled.div`
  color: #777777;
  display: inline-block;
  font-size: 13px;
`;

const Comment = ({
  accessToken,
  comment,
  mapType,
  commentTotalLikes,
  isMine,
  hasILikedListInComment,
  onCommentLikeClicked,
  changetotalLikesInComment,
  history
}) => {
  const changetotalLikes = changetotalLikesInComment.includes(comment.id)
    ? (hasILikedListInComment.includes(comment.id) ? 1 : -1) : 0;

  return (
    <CommentBox>
      <Profile imageUrl={comment.author.imageUrl}/>
      <CommentRight>
        <AuthorName>
          {comment.author.nickName}
        </AuthorName>
        <div>{comment.content}</div>
        <CommentBottom>
          <CommentLike>
            <CommentLikeInner>
              <LikeHeart
                onClick={() => {
                  changeMyLikeInCommentIdApi(accessToken,
                    hasILikedListInComment.includes(comment.id), comment.id,
                    mapType, history);
                  onCommentLikeClicked(comment.id);
                }}>{hasILikedListInComment.includes(comment.id) ? "♥"
                : "♡"} </LikeHeart>
              <LikeLetter>좋아요 {commentTotalLikes ? commentTotalLikes
                + changetotalLikes : 0 + changetotalLikes}개</LikeLetter>
            </CommentLikeInner>
          </CommentLike>
          <MyCommentControl>
            {
              isMine?
                <MyCommentControlInner>
                  <DeleteComment onClick={() => deleteCommentApi({
                    commentId: comment.id,
                    accessToken: accessToken,
                    history: history
                  })}>삭제</DeleteComment>
                </MyCommentControlInner>
              : ""
            }
          </MyCommentControl>
        </CommentBottom>
      </CommentRight>
    </CommentBox>
  );
};

export default Comment;
