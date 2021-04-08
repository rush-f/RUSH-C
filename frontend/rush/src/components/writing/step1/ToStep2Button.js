import React from 'react';
import {Link} from "react-router-dom";

const ToStep2Button = () => {
  return (
      <Link to="">
        <button style={{
          position: "fixed",
          zIndex: 10,
          bottom: 0,
          right: 0,
          width: "100px",
          height: "50px",
          margin: "10px",
        }}> 다음 </button>
      </Link>
  );
};

export default ToStep2Button;