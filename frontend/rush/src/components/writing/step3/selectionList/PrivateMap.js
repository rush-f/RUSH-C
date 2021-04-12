import React from 'react';
import StyledCheckbox from "./StyledCheckbox";
import StyledElement from "./StyledElement";

const PrivateMap = (props) => {
  return (
      <StyledElement onClick={() => props.setIsPrivateMapChecked(!props.isPrivateMapChecked)}>
        개인지도
        <StyledCheckbox
               onChange={() => props.setIsPrivateMapChecked(!props.isPrivateMapChecked)}
               checked={props.isPrivateMapChecked}
        />
      </StyledElement>
  );
};

export default PrivateMap;