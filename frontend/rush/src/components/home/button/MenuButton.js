import React, {useState} from 'react';
import {bubble as BurgerMenu} from "react-burger-menu";
import "./styled.css";
import styled from "styled-components";
import {PRIVATE, PUBLIC} from "../../../constants/MapType";
import GroupList from "../group/GroupList";

const BurgerMenuContents = styled.div`
  width: 90%;
  word-wrap: break-word;
  margin-bottom: 16px;
  font-size: 140%;
  padding: 14px;
  cursor: pointer;
`;

const Menu = ({isGroupOpened, setIsGroupOpened, setMapType, setGroupId, setIsCreateGroupModalOpen,  setIsJoinGroupModalOpen, accessToken, history}) => {

  const url = accessToken? "/mypage" : "/login";
  const [isMenuOpen,setIsMenuOpen] = useState(false);
  return (<>
    <BurgerMenu
        onOpen={()=>setIsMenuOpen(true)}
        onClose={()=>{
          setIsMenuOpen(false);
          setIsGroupOpened(false);
        }}
        isOpen={isMenuOpen}
        disableAutoFocus>
      <BurgerMenuContents onClick={() => history.push(url)}>마이페이지</BurgerMenuContents>
      <BurgerMenuContents onClick={() => setMapType(PUBLIC)}>전체지도</BurgerMenuContents>
      <BurgerMenuContents onClick={() => setIsGroupOpened(!isGroupOpened)}>
        그룹지도
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
      <BurgerMenuContents onClick={() => setMapType(PRIVATE)}>개인지도</BurgerMenuContents>
    </BurgerMenu>
  </>);
}

export default Menu;
