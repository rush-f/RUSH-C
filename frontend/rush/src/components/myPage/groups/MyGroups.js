import React from 'react';
import styled from "styled-components";
import GroupsListOpenButton from "./GroupsListOpenButton";
import GroupsList from "./GroupsList";

const StyledElement = styled.div`
  margin:10px 0;
  font-size:19px;
  padding: 4px 10px;
  cursor: pointer;
`;
const MyGroups = (props) => {
  return (<>
    <StyledElement
        onClick={() => props.setIsOpened(!props.isOpened)}
        style={{marginTop: '19px' }}
    >
      내 그룹 목록
      <GroupsListOpenButton
          isOpened={props.isOpened}
          setIsOpened={props.setIsOpened}
      />
    </StyledElement>
    <GroupsList
        isOpened={props.isOpened}
        myGroups={props.myGroups}
    />
  </>
  )
};

export default MyGroups;
