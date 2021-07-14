import React from 'react';
import styled from "styled-components";
import {withRouter} from "react-router-dom";

const ArticleContentStyle = styled.div`
  display: flex;
  justify: center;
  height: 40px;
  margin-bottom: 6px; 
  margin-left: 10px;
  border: 2px solid rgb(90, 155, 213);
  border-radius: 10px;
  overflow-x: auto;
  overflow-y: hidden;
`;

const StyledTitle = styled.div`
  white-space : nowrap;  //텍스트길이가 길어짐에 따라 버튼의 가로길이를 자동으로 증가시킴
  margin-top:5px;
  border: 2px solid rgb(90, 155, 213);
  border-radius: 10px;
  height: 30px;
  margin-left: 5px;
  font-size: 15px;
`;

const StyledButton = styled.button`
  white-space : nowrap;  //텍스트길이가 길어짐에 따라 버튼의 가로길이를 자동으로 증가시킴
  margin-top:5px;
  height: 30px;
  margin-left: 5px;
  font-size: 15px;
`;

const ArticleContent = (props) => {

  const groups= props.article.groups.map((group)=><StyledButton onClick={()=>{props.history.push('/articles/grouped/'+props.article.id)}}>{group.name}</StyledButton>);
  return (
      <ArticleContentStyle >
        <StyledTitle>{props.article.title}</StyledTitle>
        {props.article.publicMap? <StyledButton onClick={()=>{props.history.push('/articles/public/'+props.article.id)}}> 전체지도 </StyledButton>:""}
        {props.article.privateMap? <StyledButton onClick={()=>{props.history.push('/articles/private/'+props.article.id)}}>개인지도 </StyledButton>:""}
        {props.article.groups.length>=1? groups:""}
      </ArticleContentStyle>
  );
};

export default withRouter(ArticleContent);
