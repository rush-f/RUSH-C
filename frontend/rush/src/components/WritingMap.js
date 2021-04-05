import React from 'react';
import {GoogleMap, withGoogleMap, withScriptjs} from "react-google-maps";

const WritingMap = withScriptjs(withGoogleMap((props) => {
  const defaultMapOptions = {
    disableDefaultUI: true
  };

  return (
      <>
        <GoogleMap
            defaultZoom={6}
            defaultCenter={{lat: 37.397, lng: 127.644}}
            defaultOptions={defaultMapOptions}
        >
        </GoogleMap>
      </>
  );
}));

export default WritingMap;