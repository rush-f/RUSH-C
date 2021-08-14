import React, {useState} from 'react';
import {CLIENT_ID} from "../../constants/GoogleMapAuth";
import WritingMap from "./step1/WritingMap";
import ToStep2Button from "./step1/ToStep2Button";
import WritingStep2Modal from "./step2/WritingStep2Modal";
import WritingStep3Modal from "./step3/WritingStep3Modal";
import WindowSize from "../../util/WindowSize";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import {Redirect} from "react-router-dom";

const WritingPage = (props) => {
  const [step, setStep] = useState(1);
  const [center, setCenter] = useState({
    lat: () => 37.63185105917152,
    lng: () => 127.07745984005722,
  });
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const [isPublicMapChecked, setIsPublicMapChecked] = useState(false);
  const [isPrivateMapChecked, setIsPrivateMapChecked] = useState(false);
  const [checkedGroups, setCheckedGroups] = useState([]);

  if (!sessionStorage.getItem(ACCESS_TOKEN)) {
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
                    defaultCenter={props.location.state.defaultCenter}
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
            checkedGroupIds={checkedGroups}
            setCheckedGroups={setCheckedGroups}
        />
      </>
  );
};

export default WritingPage;