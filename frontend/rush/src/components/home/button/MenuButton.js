import React, {useState} from 'react';
import {bubble as BurgerMenu} from "react-burger-menu";
import "./styled.css";
import styled from "styled-components";
import GroupList from './groupMap/GroupList';
import {PRIVATE, PUBLIC} from "../../../constants/MapType";

const BurgerMenuContents = styled.div`
  width: 90%;
  word-wrap: break-word;
  margin-bottom: 16px;
  font-size: 140%;
  padding: 14px;
  cursor: pointer;
`;

const Menu = (props) => {
  const [isGroupOpened, setIsGroupOpened] = useState(false);

  return (<>
    <BurgerMenu disableAutoFocus>
      <BurgerMenuContents onClick={() => alert("아직 개발중입니다!")}>마이페이지</BurgerMenuContents>
      <BurgerMenuContents onClick={() => props.setMapType(PUBLIC)}>전체지도</BurgerMenuContents>
      <BurgerMenuContents onClick={() => setIsGroupOpened(!isGroupOpened)}>
        그룹지도
      </BurgerMenuContents>
      <GroupList
        isGroupOpened={isGroupOpened}
        setMapType={props.setMapType}
        setGroupId={props.setGroupId}
      />
      <BurgerMenuContents onClick={() => props.setMapType(PRIVATE)}>개인지도</BurgerMenuContents>
    </BurgerMenu>
  </>);
}

export default Menu;
