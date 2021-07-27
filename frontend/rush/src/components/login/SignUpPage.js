import React, {useState} from 'react';
import {HomeButton, LoginButton, StyledInput} from "./Box";
import styled from "styled-components";
import {Link} from "react-router-dom";

const SignUpPageBox = styled.div`
  position:absolute;
  top: 20%;
  width: 100%;
  text-align: center;
`;

const SignUpPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [checkPassword, setCheckPassword] = useState("");
  const [nickName, setNickName] = useState("");

  return (<>
    <Link to="/"><HomeButton/></Link>
      <SignUpPageBox>
        <StyledInput
            value={email}
            onChange={e => setEmail(e.target.value)}
            placeholder={"이메일"}
        />
        <StyledInput
            value={password}
            onChange={e => setPassword(e.target.value)}
            placeholder={"비밀번호"}
        />
        <StyledInput
            value={checkPassword}
            onChange={e => setCheckPassword(e.target.value)}
            placeholder={"비밀번호 재확인"}
        />
        <StyledInput
            value={nickName}
            onChange={e => setNickName(e.target.value)}
            placeholder={"이름"}
        />
        <LoginButton
            onClick={() => {
              if(email.length || password.length || checkPassword.length || nickName.length){
                alert("테스트중!!");
              }
              else{
                alert("빈칸을 다 채워주세요");
              }
              }}>가입하기</LoginButton>
      </SignUpPageBox>
  </>);
};

export default SignUpPage;