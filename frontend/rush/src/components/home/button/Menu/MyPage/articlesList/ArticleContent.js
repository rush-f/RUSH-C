import React from 'react';
import styled from "styled-components";
import {withRouter} from "react-router-dom";


const ArticleContentStyle = styled.div`
  display: flex;
  justify: center;
  height: 20px;
  font-size: 13px;
  margin-bottom: 6px;
  margin-left: 10px;
  border: 2px solid rgb(90, 155, 213);
  border-radius: 10px;
`;

const ArticleContent = (props) => {
  console.log(props.article)
  return (
      <ArticleContentStyle onClick={()=>{props.history.push('/')}}>
        {props.article.title}&nbsp;&nbsp;&nbsp;
        {props.article.publicMap? " 전체지도":""}
        {props.article.privateMap? " 개인지도":""}
        {props.article.groups.length>=1? " 그룹리스트나열할예정":""}
      </ArticleContentStyle>
  );
};

export default withRouter(ArticleContent);