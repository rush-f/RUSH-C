import React from 'react';
import styled from "styled-components";
import ArticlesListOpenButton from "./ArticlesListOpenButton";
import ArticlesList from "./ArticlesList";

const StyledElement = styled.div`
  margin:10px 0;
  font-size:19px;
  padding: 4px 10px;
  cursor: pointer;
`;
const MyArticles = (props) => {
  return (<>
    <StyledElement
        onClick={() => props.setIsOpened(!props.isOpened)}
        style={{marginTop: '19px' }}
    >
      내 게시글 목록
      <ArticlesListOpenButton
          isOpened={props.isOpened}
          setIsOpened={props.setIsOpened}
      />
    </StyledElement>
    <ArticlesList
        isOpened={props.isOpened}
        myArticles={props.myArticles}
    />
  </>
  )
};

export default MyArticles;
