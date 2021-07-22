import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";
import {ACCESS_TOKEN} from "../constants/SessionStorage";
import {PUBLIC} from "../constants/MapType";

const findCommentsApi = ({articleId, mapType, history}) => {
  if (mapType === PUBLIC) {
    return axios.get(BACKEND_ADDRESS + "/articles/" + mapType + "/" + articleId + "/comments")
    .then(response => response.data);
  }
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

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
  return axios.get(BACKEND_ADDRESS + "/articles/" + mapType + "/" + articleId + "/comments", config)
    .then(response => response.data);
};

export default findCommentsApi;
