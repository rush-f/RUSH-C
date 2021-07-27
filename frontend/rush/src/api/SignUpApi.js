import React from 'react';
import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const signUpApi = (email, password, nickName, history) => {
  const body = {
    email: email,
    password: password,
    nickName: nickName
  };

  axios.post(BACKEND_ADDRESS + "/auth/signup", body)
  .then(response => {
    if (response.status === 201) {
      alert("회원가입이 완료되었습니다");
      history.push("/login/email");
    }
  })
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 400) {
      alert("이미 가입되어있는 이메일 입니다");
      return Promise.reject();
    }
    alert("이유가 뭔지 모르겠지만 글쓰기 실패했음. 일단 홈화면으로...");
    history.push("/");
  });
};

export default signUpApi;