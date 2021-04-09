import React from 'react';

const PrivateMap = (props) => {
  return (
      <div>
        <input type="checkbox"
               onChange={() => props.setIsPrivateMapChecked(!props.isPrivateMapChecked)}
               checked={props.isPrivateMapChecked}
        />개인지도
      </div>
  );
};

export default PrivateMap;