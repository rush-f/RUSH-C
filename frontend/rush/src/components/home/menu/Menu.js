import React, {useEffect} from 'react';
import {bubble as BurgerMenu} from "react-burger-menu";
import "./styled.css";
import styled from "styled-components";
import {PRIVATE, PUBLIC} from "../../../constants/MapType";
import GroupList from "./group/GroupList";

const BurgerMenuContents = styled.div`
  width: 90%;
  word-wrap: break-word;
  margin-bottom: 16px;
  font-size: 140%;
  padding: 14px;
  cursor: pointer;
`;

const Menu = ({isMenuOpen, setIsMenuOpen, isGroupOpened, setIsGroupOpened, setMapType, setGroupId, setIsCreateGroupModalOpen,  setIsJoinGroupModalOpen, accessToken, history}) => {
  useEffect(()=>{
    if(!isMenuOpen)
      setIsGroupOpened(false);
  },[isMenuOpen]);

  return (<>
    <BurgerMenu
        isOpen={isMenuOpen}
        onStateChange={(state)=>{setIsMenuOpen(state.isOpen)}}
        disableAutoFocus>
      <BurgerMenuContents onClick={() => {
        setMapType(PUBLIC);
        setIsMenuOpen(false);
      }}>온누리 발자국</BurgerMenuContents>
      <BurgerMenuContents onClick={() => setIsGroupOpened(!isGroupOpened)}>
        우리누리 발자국
      </BurgerMenuContents>
      <GroupList
          setIsMenuOpen={setIsMenuOpen}
          isGroupOpened={isGroupOpened}
          setMapType={setMapType}
          setGroupId={setGroupId}
          history={history}
          setIsCreateGroupModalOpen={setIsCreateGroupModalOpen}
          setIsJoinGroupModalOpen={setIsJoinGroupModalOpen}
      />
      <BurgerMenuContents onClick={() => {
        setMapType(PRIVATE);
        setIsMenuOpen(false);
      }}>나만의 발자국</BurgerMenuContents>
    </BurgerMenu>
  </>);
}

export default Menu;
