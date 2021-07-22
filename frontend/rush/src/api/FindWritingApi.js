import axios from "axios";
import {PUBLIC} from "../constants/MapType";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";
import {ACCESS_TOKEN} from "../constants/SessionStorage";

const findWritingApi = (articleId, mapType, history) => {
  if (mapType === PUBLIC) {
    return axios.get(BACKEND_ADDRESS + "/articles/" + mapType + "/" + articleId)
      .then(response => response.data);
  }
  // 개인지도 or 그룹지도
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const config = {
    headers: {
      "Authorization": "Bearer " + accessToken
    }
  };
  return axios.get(BACKEND_ADDRESS + "/articles/" + mapType + "/" + articleId, config)
  .then(response => response.data)
    .catch(error => {
      if (error.response.status === 401 || error.response.status === 403) {
        alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
        history.push("/login");
        return Promise.reject();
      }
      alert("이유가 뭔지 모르겠지만 개인지도 글 가져오기에 실패했음...");
    });
};

export default findWritingApi;