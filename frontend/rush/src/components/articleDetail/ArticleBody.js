import React from 'react';
import ArticleTitle from "./ArticleTitle";
import ArticleContent from "./ArticleContent";

const ArticleBody = (props) => {
  return (
      <div>
        <ArticleTitle>
          {props.article ? props.article.title : ""}
        </ArticleTitle>
        <ArticleContent>
          {props.article ? props.article.content : ""}
        </ArticleContent>
      </div>
  );
};

export default ArticleBody;