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
      setLat(37.63185105917152);
      setLng(127.07745984005722);
    }
  });

  return (
      <>
        <GoogleMap
            ref={(map) => setMap(map)}
            defaultZoom={16}
            defaultCenter={{lat: 37.63185105917152, lng: 127.07745984005722}}
            defaultOptions={{
              disableDefaultUI:true,
              maxZoom:21,
              minZoom:3,
              restriction: {
                latLngBounds: {
                  north: 85,
                  south: -85,
                  west: -180,
                  east: 180,
                },
              },
            }}
            onCenterChanged={() => {
              props.setCenter(map.getCenter());
              setLat(props.center.lat);
              setLng(props.center.lng);
            }}
            onZoomChanged={() => {
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