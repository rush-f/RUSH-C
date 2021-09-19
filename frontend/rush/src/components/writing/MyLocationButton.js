import React from 'react';

const MyLocationButton = ({step, setCenter, myLocation}) => {
  const success = (position) => {
    setCenter({
      lat: () => myLocation.lat,
      lng: () => myLocation.lng
    })
  }

  const error = () => {
    alert("위치 권한을 허용해 주세요");
  }

  return <>{(step === 1) && <img
      src="/myLocation.png"
      alt="my image"
      style={{
        position: "fixed",
        zIndex: 10,
        bottom: "30px",
        right: "-22px",
        width: "150px",
        height: "150px",
        margin: "10px",
        cursor: "pointer"
      }}
      onClick={()=> navigator.geolocation.getCurrentPosition(success, error)}
  />}
  </>
};

export default MyLocationButton;