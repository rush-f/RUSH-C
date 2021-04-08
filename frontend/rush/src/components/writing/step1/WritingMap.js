import React from 'react';
import {
  GoogleMap,
  Marker,
  withGoogleMap,
  withScriptjs
} from "react-google-maps";
import {useState} from "react";

const WritingMap = withScriptjs(withGoogleMap((props) => {

  const defaultMapOptions = {
    disableDefaultUI: true
  };

  const [map, setMap] = useState(null);

  return (
      <>
        <GoogleMap
            ref={(map) => setMap(map)}
            defaultZoom={6}
            defaultCenter={{lat: 37.397, lng: 127.644}}
            defaultOptions={defaultMapOptions}
            streetView
            onCenterChanged={() => {
              props.setCenter(map.getCenter())
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