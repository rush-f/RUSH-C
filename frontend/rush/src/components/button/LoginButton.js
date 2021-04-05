import React from 'react';
import {Link} from "react-router-dom";

const LoginButton = () => {
  return (
      <Link to={"/login"}>
      <button style={{
        position: "fixed",
        zIndex: 10,
        top: 0,
        right: 0,
        width: "100px",
        height: "50px",
        margin: "10px",
      }}> 로그인 </button></Link>
  );
};

export default LoginButton;
