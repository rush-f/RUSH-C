import React from 'react';
import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

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
      alert(error.response.data.errorMessage);
      return Promise.reject();
    }
    alert("뭔지 모르지만 회원가입실패?! 홈으로..");
    history.push("/");
  });
};

export default signUpApi;