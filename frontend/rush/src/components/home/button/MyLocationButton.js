import React from 'react';

const MyLocationButton = ({defaultCenter, setMyLocation}) => {
  return (<img
          src="/myLocation.png"
          alt="my image"
          style={{
            position: "fixed",
            zIndex: 10,
            bottom: "65px",
            right: "-27px",
            width: "150px",
            height: "150px",
            margin: "10px",
            cursor: "pointer"
          }}
          onClick={()=>{setMyLocation({
            lat: defaultCenter.lat+0.00000000000000000001,
            lng: defaultCenter.lng+0.00000000000000000001,
          })}}
      />
  );
};

export default MyLocationButton;