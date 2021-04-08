import React, {useState} from 'react';
import {CLIENT_ID} from "../../../constants/GoogleMapAuth";
import WritingMap from "./WritingMap";
import ToStep2Button from "./ToStep2Button";

const WritingStep1Page = () => {
  const [center, setCenter] = useState(null);

  return (
      <>
        <WritingMap googleMapURL={CLIENT_ID}
                    loadingElement={<div style={{height: `100%`}}/>}
                    containerElement={<div style={{height: `800px`}}/>}
                    mapElement={<div style={{height: `100%`}}/>}
                    center={center}
                    centerFunc={setCenter}
        />
        <ToStep2Button />
      </>
  );
};

export default WritingStep1Page;