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
          width: "90px",
          height: "90px",
          margin: "10px",
          background: "rgb(90, 90, 90)",
          color: "white",
          borderRadius: "100px",
          border: "2px solid rgb(50, 50, 50)",
          fontSize: "110%",
          fontWeight: "bold",
          cursor: "pointer"
        }}>글쓰기</button>
      </Link>
  );
};

export default WriteButton;