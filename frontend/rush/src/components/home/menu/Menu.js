import React, {useEffect, useState} from 'react';
import {bubble as BurgerMenu} from "react-burger-menu";
import "./styled.css";
import styled from "styled-components";
import {GROUPED, PRIVATE, PUBLIC} from "../../../constants/MapType";
import GroupList from "./group/GroupList";
import findMyImportantGroupsApi
  from "../../../api/group/FindMyImportantGroupsApi";

const BurgerMenuContents = styled.div`
  width: 90%;
  word-wrap: break-word;
  margin-bottom: 16px;
  font-size: 140%;
  padding: 14px;
  cursor: pointer;
`;

const Menu = ({
  isMenuOpen,
  setIsMenuOpen,
  isGroupOpened,
  setIsGroupOpened,
  setMapType,
  setGroupId,
  setIsCreateGroupModalOpen,
  setIsJoinGroupModalOpen,
  accessToken,
  history
}) => {
  const [importantGroups, setImportantGroups] = useState([]);

  useEffect(() => {
    if (!isMenuOpen) {
      setIsGroupOpened(false);
    }
  }, [isMenuOpen]);

  useEffect(() => {
    findMyImportantGroupsApi({accessToken})
    .then(responsePromise => setImportantGroups(responsePromise));
  }, [accessToken]);

  return (<>
    <BurgerMenu
      isOpen={isMenuOpen}
      onStateChange={(state) => {
        setIsMenuOpen(state.isOpen)
      }}
      disableAutoFocus>
      {
        importantGroups.map(importantGroup =>
          <BurgerMenuContents
            onClick={() => {
            setMapType(GROUPED);
            setGroupId(importantGroup.id);
            setIsMenuOpen(false);
          }}>
            {importantGroup.name}
          </BurgerMenuContents>
        )
      }
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
