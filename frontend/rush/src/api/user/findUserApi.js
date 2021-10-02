import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findUserApi = (accessToken) => {
   const config = {
     headers: {
       "Authorization": "Bearer " + accessToken
     }
   };
  return axios.get(BACKEND_ADDRESS + "/users/me", config)
  .then(response => response.data);
};

export default findUserApi;