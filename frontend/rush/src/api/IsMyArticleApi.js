import {ACCESS_TOKEN} from "../constants/SessionStorage";
import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const isMyArticleApi = (articleId) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

  if (!accessToken) {
    return Promise.resolve(false);
  }
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }
  return axios.get(BACKEND_ADDRESS + "/articles/" + articleId + "/is-mine", config)
    .then(response => response.data);
};
export default isMyArticleApi;
