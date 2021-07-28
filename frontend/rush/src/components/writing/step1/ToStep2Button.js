import React from 'react';

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
          backgroundColor: "rgb(50, 50, 50)",
          borderWidth: "0",
          fontFamily: "'Gowun Dodum', sans-serif",
          fontSize: "105%",
          color: "white",
          borderRadius: "15px",
          borderTopLeftRadius: "0"
        }}
    > 다음 </button>
    }
  </>;
};

export default ToStep2Button;