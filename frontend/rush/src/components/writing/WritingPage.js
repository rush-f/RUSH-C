import React, {useState} from 'react';
import {CLIENT_ID} from "../../constants/GoogleMapAuth";
import WritingMap from "./step1/WritingMap";
import ToStep2Button from "./step1/ToStep2Button";
import WritingStep2Modal from "./step2/WritingStep2Modal";

const WritingPage = () => {
  const [center, setCenter] = useState(null);
  const [step, setStep] = useState(1);

  return (
      <>
        <WritingMap googleMapURL={CLIENT_ID}
                    loadingElement={<div style={{height: `100%`}}/>}
                    containerElement={<div style={{height: `800px`}}/>}
                    mapElement={<div style={{height: `100%`}}/>}
                    center={center}
                    setCenter={setCenter}
        />
        <ToStep2Button step={step} setStep={setStep} />
        <WritingStep2Modal step={step} setStep={setStep} />
      </>
  );
};

export default WritingPage;