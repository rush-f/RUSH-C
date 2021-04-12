import React from 'react';
import StyledCheckbox from "./StyledCheckbox";
import StyledElement from "./StyledElement";

const PublicMap = (props) => {
  return (
      <StyledElement onClick={() => props.setIsPublicMapChecked(!props.isPublicMapChecked)}>
        전체지도<StyledCheckbox
               onChange={() => props.setIsPublicMapChecked(!props.isPublicMapChecked)}
               checked={props.isPublicMapChecked}
        />
      </StyledElement>
  );
};

export default PublicMap;