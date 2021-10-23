import React from 'react';
import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const editArticleContentApi = ({
  newContent,
  accessToken,
  articleId,
  history
}) => {
  if (!accessToken) {
    alert("로그인이 필요한 서비스입니다.")
    history.push('/login');
    return Promise.reject("토큰이 없음");
  }
  const body = {
    newContent : newContent
  };
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }

  return axios.put(BACKEND_ADDRESS + "/articles/" + articleId, body, config)
  .then(response => {
    if (response.status === 204) {
      alert("글이 수정되었습니다 :)");
      window.location.reload();
    }
  })
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      history.push("/login");
    } else if(error.response.status === 400 || error.response.status === 404) {
      alert(error.response.data.errorMessage);
      return Promise.reject();
    } else {
      alert("글 수정 실패");
    }
    return Promise.reject();
  });
};

export default editArticleContentApi;