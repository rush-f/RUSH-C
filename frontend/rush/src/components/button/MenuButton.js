import React, {useState} from 'react';
import {bubble as BurgerMenu} from "react-burger-menu";
import "./styled.css";
import styled from "styled-components";
import Test from './Test';

const BurgerMenuContents = styled.div`
  width: 90%;
  word-wrap: break-word;
  margin-bottom: 16px;
  font-size: 140%;
  padding: 14px;
`;

const Menu = () => {
  const [isGroupOpened, setIsGroupOpened] = useState(false);
  const [height, setHeight] = useState(0);

  return (<>
    <BurgerMenu disableAutoFocus>
      <BurgerMenuContents>마이페이지</BurgerMenuContents>
      <BurgerMenuContents>전체지도</BurgerMenuContents>
      <BurgerMenuContents onClick={() => {
        setIsGroupOpened(!isGroupOpened);
        isGroupOpened? setHeight(233):setHeight(0);
      }}>그룹지도</BurgerMenuContents>
      <Test height={height}/>
      <BurgerMenuContents>개인지도</BurgerMenuContents>
    </BurgerMenu>
  </>);
}

export default Menu;
