import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findPublicMapArticles = (latitude, latitudeRange, longitude, longitudeRange) => {
  return axios.get(BACKEND_ADDRESS + "/articles/public?latitude=" + latitude + "&latitudeRange=" + latitudeRange+"&longitude=" + longitude+"&longitudeRange=" + longitudeRange)
    .then(response => response.data);
};

export default findPublicMapArticles;
