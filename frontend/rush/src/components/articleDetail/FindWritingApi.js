import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findWritingApi = (articleId, mapType) => {
  return axios.get(BACKEND_ADDRESS + "/articles/" + mapType + "/" + articleId)
    .then(response => response.data);
};

export default findWritingApi;