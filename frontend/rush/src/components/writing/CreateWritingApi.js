import React from 'react';
import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";
import {withRouter} from "react-router-dom";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";

const createWritingApi = (props) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

  if (!accessToken) {
    return Promise.reject("토큰이 이상함");
  }
  const body = {
    title: props.title,
    content: props.content,
    latitude: props.center.lat(),
    longitude: props.center.lng()
  };
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }
  axios.post(BACKEND_ADDRESS + "/articles", body, config)
    .then(response => {
      if (response.status === 201) {
         const uri = response.headers.location;

         props.history.push({pathname:uri, state:{lat:props.center.lat(), lng:props.center.lng()}});
      }
    })
    .catch(error => {
      if (error.response.status === 401 || error.response.status === 403) {
        alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
        props.history.push("/login");
        return;
      }
      alert("이유가 뭔지 모르겠지만 글쓰기 실패했음. 일단 홈화면으로...");
      props.history.push("/");
    });
};

export default createWritingApi;