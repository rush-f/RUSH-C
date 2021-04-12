import React from 'react';

const BackButton = (props) => {
  return <>
    {(props.step === 3) && <button
        onClick={() => {
          props.setStep(2);
        }}
        style={{
          position: "absolute",
          bottom: 0,
          left: 0,
          backgroundColor: "#00000000",
          width: "100px",
          height: "50px",
          margin: "10px",
        }}
    > 이전 </button>
    }
  </>;
};

export default BackButton;