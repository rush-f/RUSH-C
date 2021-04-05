import React from 'react';
import {
  GoogleMap,
  Marker,
  withGoogleMap,
  withScriptjs
} from "react-google-maps";
import {useState} from "react";

const WritingMap = withScriptjs(withGoogleMap((props) => {
  const [position, setPosition] = useState({lat: 37.397, lng: 127.644});

  const defaultMapOptions = {
    disableDefaultUI: true
  };

  return (
      <>
        <GoogleMap
            defaultZoom={6}
            defaultCenter={{lat: 37.397, lng: 127.644}}
            defaultOptions={defaultMapOptions}
            onClick={(e) => {
              setPosition({lat: e.latLng.lat(), lng: e.latLng.lng()});
            }}
        >
          <Marker
              position={position}
              animation={1}
              icon={{
                url: '/footprint.png',
              }}
              onClick={() => {
                alert();
              }}
          />
        </GoogleMap>
      </>
  );
}));

export default WritingMap;