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
        backgroundColor: "rgb(50, 50, 50)",
        borderWidth: "0",
        color: "white",
        borderRadius: "7px",
        borderTopLeftRadius: "0",
        fontSize: "110%",
        fontWeight: "bold",
        fontFamily: "'Gowun Dodum', sans-serif"
      }}> 로그인 </button></Link>
  );
};

export default LoginButton;
