import React from 'react';

const ToStep3Button = (props) => {
  return <>
    {(props.step === 2) && <button
        onClick={() => props.setStep(3)}
        style={{
          position: "absolute",
          bottom: 0,
          right: 0,
          backgroundColor: "#00000000",
          width: "100px",
          height: "50px",
          margin: "10px",
        }}
    > 다음 </button>
    }
  </>;
};

export default ToStep3Button;