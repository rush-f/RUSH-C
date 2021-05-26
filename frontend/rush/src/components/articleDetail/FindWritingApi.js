import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findWritingApi = (articleId) => {
  return axios.get(BACKEND_ADDRESS + "/articles/" + articleId)
    .then(response => response.data);
};

export default findWritingApi;