import React from 'react';
import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";

const createWritingApi = ({title, content, center, publicMap, privateMap,
    groupIds, history}) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

  if (!accessToken) {
    alert("로그인이 필요한 서비스입니다.")
    history.push('/login');
    return Promise.reject("토큰이 없음");
  }
  const body = {
    title: title,
    content: content,
    latitude: center.lat(),
    longitude: center.lng(),
    publicMap: publicMap,
    privateMap: privateMap,
    groupIdsToBeIncluded: groupIds
  };
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }
  axios.post(BACKEND_ADDRESS + "/articles", body, config)
    .then(response => {
      if (response.status === 201) {
        const backArticleUri = response.headers.location;
        const split = backArticleUri.split('/');
        const articleId = split[split.length - 1];
        const mapType = split[split.length - 2];
        if(mapType ==='grouped'){
          history.push({
            pathname: '/articles/' + mapType + "/" + articleId,
            state: {groupId: groupIds[0]}
          })
          return;
        }
        history.push("/articles/" + mapType + "/" + articleId);
      }
    })
    .catch(error => {
      if (error.response.status === 401 || error.response.status === 403) {
        alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
        history.push("/login");
        return;
      }
      alert("이유가 뭔지 모르겠지만 글쓰기 실패했음. 일단 홈화면으로...");
      history.push("/");
    });
};

export default createWritingApi;
