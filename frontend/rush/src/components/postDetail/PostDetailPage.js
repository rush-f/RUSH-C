import React from 'react';

const PostDetailPage = (props) => {
  const postId = props.match.params.postId;

  return (
      <div>
        {postId}
      </div>
  );
};

export default PostDetailPage;