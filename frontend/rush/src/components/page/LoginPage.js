import React from 'react';
import styled from "styled-components";
import {Link} from "react-router-dom";

const LoginPageBox = styled.div`
  position:absolute;
  top: 30%;
  width: 100%;
  text-align: center;
`;

const LoginButton = styled.div`
  margin: 15px auto;
  height: 45px;
  font-size: 20px;
  padding-top: 13px;
  border: 2px solid black;
  max-width: 500px;
`;

const LoginPage = () => {
  return (<>
    <Link to="/"><h1>홈</h1></Link>
      <LoginPageBox>
        <LoginButton>네이버 로그인</LoginButton>
        <LoginButton>구글 로그인</LoginButton>
      </LoginPageBox>
  </>);
};

export default LoginPage;