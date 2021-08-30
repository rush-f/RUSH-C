import React from 'react';
import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";
import {ACCESS_TOKEN} from "../constants/SessionStorage";

const emailLoginApi = (email, password, history) => {
  const body = {
    email: email,
    password: password
  };

  axios.post(BACKEND_ADDRESS + "/auth/login", body)
  .then(response => {
    if (response.status === 200) {
      sessionStorage.setItem(ACCESS_TOKEN, response.data.accessToken);
      history.push("/");
    }
  })
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 400) {
      alert("가입하지 않은 이메일이거나, 잘못된 비밀번호 입니다");
      return Promise.reject();
    }
    alert("이메일 로그인 실패!");
    history.push("/");
  });
};

export default emailLoginApi;