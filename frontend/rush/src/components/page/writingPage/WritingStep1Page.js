import React from 'react';
import {CLIENT_ID} from "../../../constants/GoogleMapAuth";
import WriteButton from "../../button/WriteButton";
import WritingMap from "../../WritingMap";

const WritingStep1Page = () => {
  return (
      <>
        <WritingMap googleMapURL={CLIENT_ID}
                    loadingElement={<div style={{height: `100%`}}/>}
                    containerElement={<div style={{height: `800px`}}/>}
                    mapElement={<div style={{height: `100%`}}/>}
        />
        <WriteButton />
      </>
  );
};

export default WritingStep1Page;