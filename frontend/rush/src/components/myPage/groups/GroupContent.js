import React from 'react';
import styled from "styled-components";
import {withRouter} from "react-router-dom";

const GroupContentStyle = styled.div`
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

const StyledButton = styled.button`
  white-space : nowrap;  //텍스트길이가 길어짐에 따라 버튼의 가로길이를 자동으로 증가시킴
  margin-top:5px;
  height: 30px;
  margin-left: 5px;
  font-size: 15px;
`;

const GroupContent = (props) => {

  return (
      <GroupContentStyle>
        {props.group? <StyledButton onClick={()=>{props.history.push('/groups/'+props.group.id)}}> {props.group.name} </StyledButton>:""}
      </GroupContentStyle>
  );
};

export default withRouter(GroupContent);
