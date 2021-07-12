import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const FindMyArticlesApi = (accessToken) => {
  const config = {
    headers: {
      "Authorization": "Bearer " + accessToken
    }
  };
  return axios.get(BACKEND_ADDRESS + "/articles/mine", config)
  .then(response => response.data);
};

export default FindMyArticlesApi;