import React, {useState} from 'react';
import styled from "styled-components";
import {LoginPageBox, LoginButton, StyledInput} from "./Box";
import emailLoginApi from "../../api/EmailLoginApi";

const StyledDiv = styled.div`
  display: inline-block;
  color:rgb(90, 155, 213);
`

const SignUpButton = styled.a`
  cursor: pointer;
`

const EmailLoginPage = (props) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  return (
      <LoginPageBox>
        <StyledInput
            value={email}
            onChange={e => setEmail(e.target.value)}
            placeholder={"이메일"}
        />
        <StyledInput
            type='password'
            value={password}
            onChange={e => setPassword(e.target.value)}
            placeholder={"비밀번호"}
        />
        <LoginButton
            onClick={() => {
              if(email.length || password.length) {
                emailLoginApi(email, password, props.history);
              }
              else {
                alert("빈칸을 다 채워주세요!");
              }
            }}>로그인</LoginButton>
        <StyledDiv>발자국</StyledDiv>
        <SignUpButton
            onClick={() => props.history.push("/login/signUp")}
        > 회원가입</SignUpButton>
      </LoginPageBox>
  );
};

export default EmailLoginPage;