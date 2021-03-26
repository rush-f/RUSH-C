import React from 'react';
import {Map, GoogleApiWrapper} from "google-maps-react";
import {CLIENT_ID} from "../constants/GoogleMapAuth";

const GoogleMap = props => {
  return (
      <>
        <Map
          google={props.google}
          zoom={15}
          initialCenter={{lat: 37.5, lng: 127}}
        />
      </>
  );
};

export default GoogleApiWrapper({
  apiKey: CLIENT_ID
})(GoogleMap);