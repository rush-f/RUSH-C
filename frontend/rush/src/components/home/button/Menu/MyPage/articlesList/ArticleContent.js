import React from 'react';
import styled from "styled-components";

const ArticleContentStyle = styled.div`
  height: 50px;
  font-size: 110%;
  margin-bottom: 6px;
  margin-left: 20px;
`;

const ArticleContent = (props) => {
  return (
      <ArticleContentStyle >
        {props.article.title}
      </ArticleContentStyle>
  );
};

export default ArticleContent;