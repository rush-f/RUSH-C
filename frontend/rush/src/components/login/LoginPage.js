import React from 'react';
import {Link} from "react-router-dom";
import {BACKEND_ADDRESS, FRONTEND_ADDRESS} from "../../constants/ADDRESS";
import {HomeButton, LoginPageBox, LoginButton} from "./Box";

const LoginPage = (props) => {
  return (<>
    <Link to="/"><HomeButton/></Link>
      <LoginPageBox>
        <LoginButton
            onClick={() => props.history.push("/login/email")}
        >이메일로 로그인</LoginButton>
        <LoginButton
            href={ BACKEND_ADDRESS + "/oauth2/authorize/naver?"
            + "redirect_uri=" + FRONTEND_ADDRESS +"/oauth2/redirect" }
        >네이버로그인</LoginButton>
        <LoginButton
            href={ BACKEND_ADDRESS + "/oauth2/authorize/google?"
            + "redirect_uri=" + FRONTEND_ADDRESS +"/oauth2/redirect" }
        >구글로그인</LoginButton>
      </LoginPageBox>
  </>);
};

export default LoginPage;