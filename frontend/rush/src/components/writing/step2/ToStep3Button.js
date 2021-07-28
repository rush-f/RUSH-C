import React from 'react';

const ToStep3Button = (props) => {
  return <>
    {(props.step === 2) && <button
        onClick={() => {
          if (props.isWritingCompleted) {
            props.setStep(3);
            return;
          }
          alert("제목 또는 내용이 비어있습니다!");
        }}
        style={{
          backgroundColor: "white",
          borderBottomRightRadius: "50px",
          borderTopRightRadius: "50px",
          border: "2px solid rgb(90, 90, 90)",
          width: "100px",
          height: "50px",
          margin: "5px 10px 10px 3px",
          fontFamily: "'Gowun Dodum', sans-serif",
          textAlign: "left",
          paddingLeft: "30px"
        }}
    >다음</button>
    }
  </>;
};

export default ToStep3Button;
