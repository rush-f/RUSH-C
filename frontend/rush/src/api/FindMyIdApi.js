import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const findMyIdApi = ({accessToken}) => {
  if (!accessToken) {
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
