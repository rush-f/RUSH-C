import React from 'react';

const PublicMap = (props) => {
  return (
      <div>
        <input type="checkbox"
               onChange={() => props.setIsPublicMapChecked(!props.isPublicMapChecked)}
               checked={props.isPublicMapChecked}
        />
        전체지도
      </div>
  );
};

export default PublicMap;