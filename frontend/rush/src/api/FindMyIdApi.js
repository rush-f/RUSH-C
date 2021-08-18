import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const findMyIdApi = ({accessToken, history}) => {
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
  return axios.get(BACKEND_ADDRESS + "/users/me/id", config)
    .then(response => response.data);
};
export default findMyIdApi;
