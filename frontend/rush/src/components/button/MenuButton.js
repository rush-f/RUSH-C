import React from 'react';
import {bubble as BurgerMenu} from "react-burger-menu";
import "./styled.css";
import styled from "styled-components";

const BurgerMenuContents = styled.div`
  width: 90%;
  word-wrap: break-word;
  margin-bottom: 16px;
  font-size: 140%;
  padding: 14px;
`;

const Menu = () => {
  const showSettings = event => {
    event.preventDefault();
  };

  return (<>
    <BurgerMenu disableAutoFocus>
      <BurgerMenuContents>마이페이지</BurgerMenuContents>
      <BurgerMenuContents>전체지도</BurgerMenuContents>
      <BurgerMenuContents>그룹지도</BurgerMenuContents>
      <BurgerMenuContents>개인지도</BurgerMenuContents>
    </BurgerMenu>
  </>);
};

export default Menu;
