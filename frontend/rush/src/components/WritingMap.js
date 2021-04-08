import React from 'react';
import {
  GoogleMap,
  Marker,
  withGoogleMap,
  withScriptjs
} from "react-google-maps";
import {useState} from "react";

const WritingMap = withScriptjs(withGoogleMap((props) => {
  const [center, setCenter] = useState({lat: null, lng: null});

  const [map, setMap] = useState(null);

  const defaultMapOptions = {
    disableDefaultUI: true
  };

  return (
      <>
        <GoogleMap
            ref={(map) => setMap(map)}
            defaultZoom={6}
            defaultCenter={{lat: 37.397, lng: 127.644}}
            defaultOptions={defaultMapOptions}
            streetView
            onCenterChanged={() => {
              setCenter(map.getCenter())
            }}
        >
          <Marker
              position={center}
              icon={{
                url: '/footprint.png',
              }}
          />
        </GoogleMap>
      </>
  );
}));

export default WritingMap;