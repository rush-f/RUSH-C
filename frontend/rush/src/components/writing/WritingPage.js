import React, {useState} from 'react';
import {CLIENT_ID} from "../../constants/GoogleMapAuth";
import WritingMap from "./step1/WritingMap";
import ToStep2Button from "./step1/ToStep2Button";
import WritingStep2Modal from "./step2/WritingStep2Modal";
import WritingStep3Modal from "./step3/WritingStep3Modal";
import WindowSize from "../WindowSize";
import {ACCESS_TOKEN} from "../../constants/LocalStorage";
import {Redirect} from "react-router-dom";

const WritingPage = () => {
  const [step, setStep] = useState(1);
  const [center, setCenter] = useState({
    lat: () => 37.397,
    lng: () => 127.644,
  });
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const [isPublicMapChecked, setIsPublicMapChecked] = useState(false);
  const [isPrivateMapChecked, setIsPrivateMapChecked] = useState(false);
  const [checkedGroups, setCheckedGroups] = useState([]);

  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return <Redirect to={{
      pathname: "/login"
    }}/>
  }
  return (
      <>
        <WritingMap googleMapURL={CLIENT_ID}
                    loadingElement={<div style={{height: `100%`}}/>}
                    containerElement={<div style={{height: WindowSize().height}}/>}
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
        <WritingStep3Modal
            step={step}
            setStep={setStep}
            title={title}
            content={content}
            center={center}
            isPublicMapChecked={isPublicMapChecked}
            setIsPublicMapChecked={setIsPublicMapChecked}
            isPrivateMapChecked={isPrivateMapChecked}
            setIsPrivateMapChecked={setIsPrivateMapChecked}
            checkedGroups={checkedGroups}
            setCheckedGroups={setCheckedGroups}
        />
      </>
  );
};

export default WritingPage;