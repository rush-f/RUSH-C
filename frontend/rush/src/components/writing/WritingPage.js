import React, {useState} from 'react';
import {CLIENT_ID} from "../../constants/GoogleMapAuth";
import WritingMap from "./step1/WritingMap";
import ToStep2Button from "./step1/ToStep2Button";
import WritingStep2Modal from "./step2/WritingStep2Modal";
import WritingStep3Modal from "./step3/WritingStep3Modal";

const WritingPage = () => {
  const [step, setStep] = useState(1);

  const [center, setCenter] = useState(null);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

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
        <WritingStep2Modal
            step={step}
            setStep={setStep}
            title={title}
            setTitle={setTitle}
            content={content}
            setContent={setContent}
        />
        <WritingStep3Modal step={step} setStep={setStep} />
      </>
  );
};

export default WritingPage;