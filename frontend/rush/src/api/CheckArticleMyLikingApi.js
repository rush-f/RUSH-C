import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const checkArticleMyLikingApi = (accessToken, articleId) => {
  const config = {
    headers: {
      "Authorization": "Bearer " + accessToken
    }
  };
  return axios.get(BACKEND_ADDRESS + "/liking/article/check?article_id="+articleId, config)
  .then(response => response.data);
};

export default checkArticleMyLikingApi;