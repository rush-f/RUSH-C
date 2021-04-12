import React from 'react';
import styled from "styled-components";

const StyledInput = styled.input`
  margin-right: 10px;
`;

const StyledCheckbox = (props) => {
  return <StyledInput
      type="checkbox"
      onChange={props.onChange}
      checked={props.checked}
  />;
};

export default StyledCheckbox;