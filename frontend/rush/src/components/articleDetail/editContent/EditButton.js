import React from 'react';
import styled from "styled-components";

const EditButtonStyle = styled.div`
  color: #777777;
  display: inline-block;
  cursor: pointer;
  margin-right : 15px;
`;
const EditButton = (props) => {
  return (
      <EditButtonStyle onClick={() => {
        props.setIsChangeContentModalOpened(true);
      }}>
        수정
      </EditButtonStyle>
  );
};

export default EditButton;