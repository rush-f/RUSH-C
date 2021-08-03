import React from 'react';
import styled from "styled-components";

const DeleteButtonStyle = styled.div`
  color: #777777;
  display: inline-block;
`;

const DeleteButton = () => {
  return (
    <DeleteButtonStyle>
      삭제
    </DeleteButtonStyle>
  );
};

export default DeleteButton;
