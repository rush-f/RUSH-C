import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";

const createCommentApi = (content, articleId, accessToken, history) => {
  const config = {
    headers: {
      "Authorization": "Bearer " + accessToken
    }
  };
  const body = {
    'content': content
  };
  return axios.post(BACKEND_ADDRESS + "/comments?article_id=" + articleId, body, config)
    .then(response => {
      if (response.status === 201) {
        return response.data;
      }
    })
    .catch(error => {
      if (error.response.status === 401 || error.response.status === 403) {
        alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
        history.push("/login");
        return true;
      }
      alert("이유가 뭔지 모르겠지만 댓글작성 실패했음.");
    });
};

export default createCommentApi;
