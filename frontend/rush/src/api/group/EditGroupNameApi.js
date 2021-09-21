import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const editGroupNameApi = ({ groupId, newGroupName, accessToken, history }) => {
  if (!accessToken) {
    alert("로그인이 필요한 서비스입니다.")
    history.push('/login');
    return Promise.reject("토큰이 없음");
  }
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  };
  const body = {
    newName: newGroupName
  };
  return axios.put(BACKEND_ADDRESS + "/groups/" + groupId, body, config)
    .catch(error => {
      if (error.response.status === 401 || error.response.status === 403) {
        alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
        history.push("/login");
      } else {
        alert("그룹이름 변경 실패");
      }
      return Promise.reject();
    });
};

export default editGroupNameApi;
