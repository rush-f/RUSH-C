import styled from "styled-components";

const Outside = styled.div`
  max-width: 400px;
  margin: auto;
`;

const DisplayBox = styled.div`
  margin: 10px;
  border: 2px solid rgb(90, 155, 213);
  border-radius: 10px;
  overflow-y: scroll;
`;

const PostBox = styled.div`
  margin: 0;
  border-bottom: 2px solid rgb(90, 155, 213);
  padding: 10px;
`;

const CommentsBox = styled.div`
  margin: 0;
`;

export { Outside, DisplayBox, PostBox, CommentsBox };
