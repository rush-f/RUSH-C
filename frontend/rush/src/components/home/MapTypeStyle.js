import React from 'react';
import {GROUPED, PRIVATE, PUBLIC} from "../../constants/MapType";
import styled from "styled-components";

const Style = styled.div`
  display: inline-block;
  font-size: 15px;
  background: rgba(222, 235, 247, 0.6);
  border-radius: 20px;
  cursor: pointer;
  position: fixed;
  zIndex: 10;
  top: 0;
  right: 80px;
  width: 100px;
  margin: 10px;
  text-align: center;
  padding: 5px 0;
  font-weight: bold;
`;

const MapType = ({mapType, groupId, groupName, history}) => {
  if (mapType === GROUPED) {
    return <Style onClick={() => history.push("/groups/" + groupId)}>
      {groupName}
    </Style>
  }
  if (mapType === PUBLIC) {
    return <Style>전체지도</Style>;
  }
  if (mapType === PRIVATE) {
    return <Style>부분지도</Style>;
  }
};

export default MapType;