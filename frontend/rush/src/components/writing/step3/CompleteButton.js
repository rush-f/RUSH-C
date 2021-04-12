import React from 'react';

const CompleteButton = (props) => {
  return <>
    {(props.step === 3) && <button
        onClick={() => {
          alert('글쓰기완료');
        }}
        style={{
          position: "absolute",
          bottom: 0,
          right: 0,
          backgroundColor: "#00000000",
          width: "100px",
          height: "50px",
          margin: "10px",
        }}
    > 완료 </button>
    }
  </>;
};

export default CompleteButton;