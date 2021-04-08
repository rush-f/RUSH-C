import React from 'react';
import {Link} from "react-router-dom";

const ToStep2Button = (props) => {
  return <>
    {(props.step === 1) && <button
        onClick={() => props.setStep(2)}
        style={{
          position: "fixed",
          zIndex: 10,
          bottom: 0,
          right: 0,
          width: "100px",
          height: "50px",
          margin: "10px",
        }}
    > 다음 </button>
    }
  </>;
};

export default ToStep2Button;