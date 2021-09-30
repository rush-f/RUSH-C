import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findUserApi = (accessToken) => {
   const config = {
     headers: {
       "Authorization": "Bearer " + accessToken
     }
   };
  return axios.get(BACKEND_ADDRESS + "/users/me", config)
  .then(response => {
    if (response.status === 200) {
      return response.data
    }
  })
  .catch(error => {
    if(error.response.status === 400 || error.response.status === 404) {
      alert(error.response.data.errorMessage);
      return Promise.reject();
    } else alert("이유가 뭔지 모르겠지만 내 정보를 불러오는데 실패했음...");
  });
};

export default findUserApi;