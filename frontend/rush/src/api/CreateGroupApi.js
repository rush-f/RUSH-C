import {ACCESS_TOKEN} from "../constants/SessionStorage";
import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const createGroupApi = ({groupName, history}) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

  if (!accessToken) {
    alert("로그인이 필요한 서비스입니다.")
    history.push('/login');
    return Promise.reject("토큰이 없음");
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
      return Promise.reject();
    }
    alert("그룹 가입 실패");
  });
};

export default createGroupApi;
