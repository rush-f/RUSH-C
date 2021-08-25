import React from 'react';
import styled from "styled-components";
import {withRouter} from "react-router-dom";

const GroupContentStyle = styled.div`
  display: flex;
  justify: center;
  height: 30px;
  margin-bottom: 6px; 
  margin-left: 10px;
  border-bottom: 2px solid rgb(90, 155, 213);
  cursor: pointer;
`;

const GroupContent = (props) => {

  return (
      <GroupContentStyle onClick={()=>{props.history.push('/groups/'+props.group.id)}}>
        {props.group.name}
      </GroupContentStyle>
  );
};

export default withRouter(GroupContent);
