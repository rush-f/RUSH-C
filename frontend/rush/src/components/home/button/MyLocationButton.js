import React from 'react';

const MyLocationButton = ({defaultCenter, setDefaultCenter}) => {
  return (<img
          src="/myLocation.png"
          alt="my image"
          style={{
            position: "fixed",
            zIndex: 10,
            bottom: "60px",
            right: "-30px",
            width: "160px",
            height: "160px",
            margin: "10px",
            cursor: "pointer"
          }}
      />
  );
};

export default MyLocationButton;