import React from 'react';
import styled from "styled-components";

const StyledDiv = styled.div`
  display: inline-block;
  background-color: rgb(90, 155, 213);
  width:80%;
  font-size:25px;
  text-align: center;
  cursor:pointer;
  padding: 4px 10px;
  color: white;
  border-radius: 10px;
  box-shadow: 5px 3px 4px 3px #446688;
  margin-bottom:15px;
`;

const SelectAllButton = (props) => {

  const onClick = ()=>{
    if(props.isPublicMapChecked || props.isPrivateMapChecked || props.checkedGroups.length > 0){
      props.setCheckedGroups([]);
      props.setIsPublicMapChecked(false);
      props.setIsPrivateMapChecked(false);
    }
    else {
      props.setCheckedGroups(props.groups.map(group => group.id));
      props.setIsPublicMapChecked(true)
      props.setIsPrivateMapChecked(true)
    }
  };

  return (
      <div>
        <StyledDiv onClick={onClick} >전체선택</StyledDiv>
      </div>
  );
};

export default SelectAllButton;