import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const findCommentsApi = (articleId) => {
  return axios.get(BACKEND_ADDRESS + "/comments?article_id=" + articleId)
    .then(response => response.data);
};

export default findCommentsApi;
