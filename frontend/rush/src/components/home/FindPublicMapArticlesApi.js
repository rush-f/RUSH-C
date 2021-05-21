import axios from "axios";
import {ADDRESS} from "../../constants/BackendAddress";

const findPublicMapArticles = (latitude, longitude) => {
  return axios.get(ADDRESS + "/articles/public?latitude=" + latitude + "&longitude=" + longitude)
    .then(response => response.data);
};

export default findPublicMapArticles;
