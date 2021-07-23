import {ACCESS_TOKEN} from "../constants/SessionStorage";
import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const joinGroupApi = ({invitationCode, history}) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

  if (!accessToken) {
    alert("로그인이 필요한 서비스입니다.")
    history.push('/login');
    return Promise.reject("토큰이 없음");
  }
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }
  axios.post(BACKEND_ADDRESS + "/groups/join?invitation_code=" + invitationCode, null, config)
  .then(response => {
    if (response.status === 201) {
      const backGroupUri = response.headers.location;
      const split = backGroupUri.split('/');
      const groupId = split[split.length - 1];

      history.push("/groups/" + groupId);
    }
  })
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      history.push("/login");
      return Promise.reject();
    }
    alert("이유가 뭔지 모르겠지만 그룹 가입에 실패했음...");
  });
};

export default joinGroupApi;
