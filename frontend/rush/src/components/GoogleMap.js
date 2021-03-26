import React from 'react';
import {Map, GoogleApiWrapper, Marker} from "google-maps-react";
import {CLIENT_ID} from "../constants/GoogleMapAuth";

const GoogleMap = props => {

  const positions = [
    { lat: 37.554722, lng: 126.970833 },
    { lat: 37.55, lng: 126.970833 },
    { lat: 37.54, lng: 126.970833 },
    { lat: 37.53, lng: 126.970833 },
    { lat: 37.52, lng: 126.970833 },
    { lat: 37.51, lng: 126.970833 },
    { lat: 37.50, lng: 126.970833 },
    { lat: 37.49, lng: 126.970833 },
    { lat: 37.48, lng: 126.970833 },
    { lat: 37.47, lng: 126.970833 },
    { lat: 37.46, lng: 126.970833 },
    { lat: 37.45, lng: 126.970833 },
  ];

  const markers = positions.map((position, index) => <Marker
      key={index}
      position={position}
      animation={1}
      onClick={() => {alert(index);}}
  />);

  return (
      <>
        <Map
          google={props.google}
          zoom={10}
          initialCenter={{lat: 37.50, lng: 126.970833 }}
        >
          {markers}
        </Map>
      </>
  );
};

export default GoogleApiWrapper({
  apiKey: CLIENT_ID
})(GoogleMap);