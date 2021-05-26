import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findPublicMapArticles = (latitude, longitude) => {
  return axios.get(BACKEND_ADDRESS + "/articles/public?latitude=" + latitude + "&longitude=" + longitude)
    .then(response => response.data);
};

export default findPublicMapArticles;
