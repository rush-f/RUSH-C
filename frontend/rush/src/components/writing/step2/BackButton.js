import React from 'react';

const BackButton = (props) => {
  return <>
    {(props.step === 2) && <button
        onClick={() => {
          props.setStep(1);
        }}
        style={{
          backgroundColor: "white",
          borderBottomLeftRadius: "50px",
          borderTopLeftRadius: "50px",
          border: "2px solid rgb(90, 90, 90)",
          width: "100px",
          height: "50px",
          margin: "5px 3px 10px 10px",
          fontFamily: "'Gowun Dodum', sans-serif",
          textAlign: "right",
          paddingRight: "30px"
        }}
    > 이전 </button>
    }
  </>;
};

export default BackButton;