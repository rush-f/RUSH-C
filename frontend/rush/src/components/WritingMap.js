import React from 'react';
import {
  GoogleMap,
  Marker,
  withGoogleMap,
  withScriptjs
} from "react-google-maps";
import {useState} from "react";

const WritingMap = withScriptjs(withGoogleMap((props) => {
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
              props.centerFunc(map.getCenter())
            }}
        >
          <Marker
              position={props.center}
              icon={{
                url: '/footprint.png',
              }}
          />
        </GoogleMap>
      </>
  );
}));

export default WritingMap;