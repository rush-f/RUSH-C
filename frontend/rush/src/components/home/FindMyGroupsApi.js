import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findMyGroupsApi = (history) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

  if (!accessToken) {
    return Promise.reject("토큰이 이상함");
  }
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }
  return axios.get(BACKEND_ADDRESS + "/groups/mine", config)
  .then(response => {
    if (response.status === 200) {
      return response.data
    }
  })
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      history.push("/login");
      return;
    }
    alert("이유가 뭔지 모르겠지만 내 그룹을 불러오는데 실패했음...");
  });
};

export default findMyGroupsApi;
