import React from 'react';

const MyLocationButton = ({step, setCenter, myLocation}) => {
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
      onClick={() => {
        setCenter({
          lat: () => myLocation.lat,
          lng: () => myLocation.lng
        })
      }}
  />}
  </>
};

export default MyLocationButton;