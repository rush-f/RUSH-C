import React from 'react';
import {
  GoogleMap,
  Marker,
  withGoogleMap,
  withScriptjs
} from "react-google-maps";
import {useState, useEffect} from "react";

const WritingMap = withScriptjs(withGoogleMap((props) => {

  const defaultMapOptions = {
    disableDefaultUI: true
  };

  const [map, setMap] = useState(null);
  const [lat, setLat] = useState(null);
  const [lng, setLng] = useState(null);

  useEffect(() => {
    if (lat == null) {
      setLat(37.397);
      setLng(127.644);
    }
  });

  return (
      <>
        <GoogleMap
            ref={(map) => setMap(map)}
            defaultZoom={6}
            defaultCenter={{lat: 37.397, lng: 127.644}}
            defaultOptions={defaultMapOptions}
            streetView
            onCenterChanged={() => {
              props.setCenter(map.getCenter());
              setLat(props.center.lat);
              setLng(props.center.lng);
            }}
        >
          <Marker
              position={{lat: lat, lng: lng}}
              icon={{
                url: '/footprint.png',
              }}
          />
        </GoogleMap>
      </>
  );
}));

export default WritingMap;