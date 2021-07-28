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
          backgroundColor: "rgb(50, 50, 50)",
          borderWidth: "0",
          color: "white",
          borderRadius: "100px",
          fontFamily: "'Gowun Dodum', sans-serif",
          fontSize: "110%",
          fontWeight: "bold",
          cursor: "pointer"
        }}>글쓰기</button>
      </Link>
  );
};

export default WriteButton;