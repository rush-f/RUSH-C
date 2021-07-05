import {ACCESS_TOKEN} from "../../../../constants/SessionStorage";
import axios from "axios";
import {BACKEND_ADDRESS} from "../../../../constants/ADDRESS";

const createGroupApi = ({groupName, history}) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

  if (!accessToken) {
    return Promise.reject("로그인이 필요합니다.");
  }
  const body = {
    name: groupName
  };
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }
  axios.post(BACKEND_ADDRESS + "/groups", body, config)
  .then(response => {
    if (response.status === 201) {
      const uri = response.headers.location;
      history.push(uri);
    }
  })
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      history.push("/login");
      return;
    }
    alert("이유가 뭔지 모르겠지만 그룹 만들기에 실패했음...");
  });
};

export default createGroupApi;
