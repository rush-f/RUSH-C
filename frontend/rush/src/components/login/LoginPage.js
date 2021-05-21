import React from 'react';
import styled from "styled-components";
import {Link} from "react-router-dom";
import axios from "axios";

const LoginPageBox = styled.div`
  position:absolute;
  top: 30%;
  width: 100%;
  text-align: center;
`;

const LoginButton = styled.a`
  display: block;
  margin: 15px auto;
  height: 45px;
  font-size: 20px;
  padding-top: 13px;
  border: 2px solid black;
  max-width: 500px;
  text-decoration: none;
  color: black;
  &:hover {
    color:#00A0C6; 
    text-decoration:none; 
    cursor:pointer;  
  }
`;

const HomeButton = styled.div`
  width: 50px;
  height: 50px;
  display: inline-block;
  background-image: url('/home.png');
  background-size: contain;
`;

const LoginPage = () => {
  return (<>
    <Link to="/"><HomeButton/></Link>
      <LoginPageBox>
        <LoginButton>네이버 로그인</LoginButton>
        <LoginButton href="http://localhost:8080/oauth2/authorize/google?redirect_uri=http://localhost:3000/oauth2/redirect">구글로그인</LoginButton>
      </LoginPageBox>
  </>);
};

export default LoginPage;