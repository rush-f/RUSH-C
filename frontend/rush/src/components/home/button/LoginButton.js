import React from 'react';
import {Link} from "react-router-dom";

const LoginButton = () => {
  return (
      <Link to="/login">
      <button style={{
        position: "fixed",
        zIndex: 10,
        top: 0,
        right: 0,
        width: "100px",
        height: "40px",
        margin: "10px",
        border: "2px solid black",
        background: "rgb(222, 235, 247)",
        borderRadius: "50px",
        fontSize: "110%",
        fontWeight: "bold",
        fontFamily: "'Gowun Dodum', sans-serif"
      }}> 로그인 </button></Link>
  );
};

export default LoginButton;
