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
        height: "35px",
        margin: "10px",
        border: "1px solid rgb(30, 30, 30)",
        borderBottom: "3px solid rgb(30, 30, 30)",
        background: "rgb(70, 70, 70)",
        color: "white",
        boxShadow: "0 -10px 10px rgb(60, 60, 60) inset",
        borderRadius: "7px",
        borderTopLeftRadius: "0",
        fontSize: "110%",
        fontWeight: "bold",
        fontFamily: "'Gowun Dodum', sans-serif"
      }}> 로그인 </button></Link>
  );
};

export default LoginButton;
