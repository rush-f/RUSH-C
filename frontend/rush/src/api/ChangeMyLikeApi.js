import React from 'react';
import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const changeMyLikeApi = (accessToken, hasILiked, articleId,history) => {
  if (!accessToken) {
    alert("로그인이 필요한 서비스입니다.")
    history.push('/login');
    return Promise.reject("토큰이 없음");
  }

  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }

  axios.post(BACKEND_ADDRESS + "/articles/"+articleId+"/like?hasiliked="+hasILiked,{}, config)
  .then(response => {
    if (response.status === 201) {
    }
  })
  .catch(error => {
    if (error.status === 401 || error.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      history.push("/login");
      return Promise.reject();
    }
    alert("이유가 뭔지 모르겠지만 좋아요 실패했음.");
    history.push("/");
  });
};

export default changeMyLikeApi;