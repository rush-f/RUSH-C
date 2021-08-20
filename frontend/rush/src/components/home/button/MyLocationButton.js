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
          onClick={()=>{setDefaultCenter({
            lat: defaultCenter.lat+0.00000000000000000001,
            lng: defaultCenter.lng+0.00000000000000000001,
          });}}
      />
  );
};

export default MyLocationButton;