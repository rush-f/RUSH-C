import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findMyImportantGroupsApi = ({accessToken}) => {
  if (!accessToken) {
    return Promise.resolve([]);
  }
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }
  return axios.get(BACKEND_ADDRESS + "/groups/mine/important", config)
  .then(response => {
    if (response.status === 200) {
      return response.data
    }
  })
  .catch(() => Promise.resolve([]));
};

export default findMyImportantGroupsApi;
