import React from 'react';
import {Link} from "react-router-dom";

const WriteButton = (props) => {
  const url = props.accessToken? "/writing" : "/login";

  return (
      <Link to={url}>
        <button style={{
          position: "fixed",
          zIndex: 10,
          bottom: 0,
          right: 0,
          width: "100px",
          height: "100px",
          margin: "10px",
          background: "rgb(222, 235, 247)",
          borderRadius: "100px",
          border: "2px solid black",
          fontSize: "110%",
          fontWeight: "bold",
          cursor: "pointer"
        }}>글쓰기</button>
      </Link>
  );
};

export default WriteButton;