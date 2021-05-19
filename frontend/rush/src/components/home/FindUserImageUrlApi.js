import axios from "axios";
import {ADDRESS} from "../../constants/BackendAddress";

const findUserImageUrlApi = (accessToken) => {
   const config = {
     headers: {
       "Authorization": "Bearer " + accessToken
     }
   };
  return axios.get(ADDRESS + "/users/me/image", config)
  .then(response => response.data);
};

export default findUserImageUrlApi;