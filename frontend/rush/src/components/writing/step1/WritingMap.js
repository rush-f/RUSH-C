import React from 'react';
import {
  GoogleMap,
  Marker,
  withGoogleMap,
  withScriptjs
} from "react-google-maps";
import {useState, useEffect} from "react";

const WritingMap = withScriptjs(withGoogleMap((props) => {

  const [map, setMap] = useState(null);
  const [lat, setLat] = useState(null);
  const [lng, setLng] = useState(null);

  useEffect(() => {
    if (lat == null) {
      setLat(props.defaultCenter.lat);
      setLng(props.defaultCenter.lng);
    }
  });

  return (
      <>
        <GoogleMap
            ref={(map) => setMap(map)}
            defaultZoom={16}
            center={props.defaultCenter}
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