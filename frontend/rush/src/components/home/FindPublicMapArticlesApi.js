import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findMapArticles = (latitude, latitudeRange, longitude, longitudeRange, mapType) => {
  return axios.get(BACKEND_ADDRESS + "/articles/" + mapType
    + "?latitude=" + latitude
    + "&latitudeRange=" + latitudeRange
    + "&longitude=" + longitude
    + "&longitudeRange=" + longitudeRange)
  .then(response => response.data);
};

export default findMapArticles;
