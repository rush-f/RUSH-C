import React from 'react';

const MyLocationButton = ({setCenter, defaultCenter, setMyLocation}) => {
  const success = (position) => {
    setCenter({
      lat: () => defaultCenter.lat,
      lng: () => defaultCenter.lng
    })
    setMyLocation({
      lat: defaultCenter.lat+0.00000000000000000001,
      lng: defaultCenter.lng+0.00000000000000000001,
    })
  }

  const error = () => {
    alert("위치 권한을 허용해 주세요");
  }

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
          onClick={()=> navigator.geolocation.getCurrentPosition(success, error)}
      />
  );
};

export default MyLocationButton;